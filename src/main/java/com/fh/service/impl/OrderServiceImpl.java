package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.CountException;
import com.fh.common.enums.PayStatusEnum;
import com.fh.mapper.OrderMapper;
import com.fh.mapper.OrderShopMapper;
import com.fh.mapper.ShopMapper;
import com.fh.model.Order;
import com.fh.model.OrderShop;
import com.fh.model.Shop;
import com.fh.model.ShopCar;
import com.fh.service.OrderService;
import com.fh.utils.RedisUse;
import com.github.wxpay.sdk.FeiConfig;
import com.github.wxpay.sdk.WXPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private ShopMapper shopMapper;
    @Resource
    private OrderShopMapper orderShopMapper;
    @Autowired
    private HttpServletRequest request;
    @Override
    public Map createOrder(Integer addareaId, Integer payType)throws CountException {
        //此请求返回的数据
        Map map = new HashMap();
        //订单详情表
        List<OrderShop> list = new ArrayList<>();
        //完善数据  保存数据库
        Order order = new Order();
        order.setAddareaId(addareaId);
        order.setCreateDate(new Date());
        order.setPayType(payType);
        order.setPayStatus(PayStatusEnum.PAY_STATUS_INIT.getStatus());
        //设置商品的清单个数   商品的清单怎么拿  前提库存要足够
        Integer typeCount= 0;
        //设置总金额
        BigDecimal  totalMoney = new BigDecimal(0);
        //获取购物车的key  登录信息  request
        Map login_user = (Map) request.getAttribute("login_user");
        String  iphone = (String) login_user.get("iphone");
        //获取购物车  商品的信息是string  全部的
        List<String> shopStr = RedisUse.hvals("CarController_" + iphone);
        for (int i = 0; i <shopStr.size() ; i++) {
            //将字符串转为javabean
            ShopCar shopCar = JSONObject.parseObject(shopStr.get(i), ShopCar.class);
            //判断是否为订单中的 商品
            if(shopCar.isCheck()==true){
                //查询数据库信息   判断库存是否足够
                Shop shop = shopMapper.selectById(shopCar.getId());
                if (shop.getStoryCount()>=shopCar.getCount()){//库存足够 数据库的库存大于购物车的库存
                    // order 的商品个数加1  计算金额
                    typeCount++;
                    totalMoney = totalMoney.add(shopCar.getMoney());

                    //维护订单详情表
                    OrderShop orderShop = new OrderShop();
                    orderShop.setAllCount(shopCar.getCount());
                    orderShop.setShopductId(shopCar.getId());
                    list.add(orderShop);//没有订单id
                    //减库存  更新sql语句
                    //数据库锁   让他不会产生超卖
                    int hh = shopMapper.updateShopCount(shop.getId(), shopCar.getCount());
                    if (hh==0){//超卖
                        throw new CountException(shopCar.getName()+"商品库存不足   现库存："+shop.getStoryCount());
                    }
                }else {
                    throw new CountException(shopCar.getName()+"商品库存不足   库存现有："+shop.getStoryCount());
                }
            }
        }
        order.setShopTypeCount(typeCount);
        order.setTotalMoney(totalMoney);
        orderMapper.insertOreder(order);
        //保存订单详情表
        orderShopMapper.batchAdd(list,order.getId());
        //删除redis中的数据  把购物车中已经结算的商品 移除redis
        for (int i = 0; i < shopStr.size(); i++) {
            //将字符串转为javabean
            ShopCar shopCar = JSONObject.parseObject(shopStr.get(i), ShopCar.class);
            if (shopCar.isCheck()==true){
                RedisUse.hdel("CarController_"+iphone,shopCar.getId()+"");
            }
        }
        map.put("code",200);
        map.put("orderId",order.getId());
        map.put("totalMoney",totalMoney);
        return map;
    }

    @Override
    public Map createMeonyPhoto(Integer orderId) throws Exception {
        Map rs  = new HashMap();
        Order order = orderMapper.selectById(orderId);
        //微信支付   商户生成二维码
        //配置配置信息
        FeiConfig config = new FeiConfig();
        //得到微信支付对象
        WXPay wxpay = new WXPay(config);
        //设置请求参数
        Map<String,String> data = new HashMap<String,String>();
        //对订单信息描述
        data.put("body","飞狐电商666—订单支付");
        //String payId = System.currentTimeMillis()+"";
        //设置订单号（保证唯一）
        data.put("out_trade_no","weixin1_order_lsc_"+orderId);
        //设置币种
        data.put("fee_type","CNY");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        Date d=new Date();
        String dateStr = sdf.format(new Date(d.getTime() + 120000000));
        //设置二维码的失效时间
        data.put("time_expire",dateStr);
        //元转分
        BigDecimal amount = new BigDecimal(String.valueOf(order.getTotalMoney()));//单位元
        amount = amount.multiply(new BigDecimal(100));// 单位分
        amount = amount.setScale(0, RoundingMode.DOWN);//取整
        String payAmt = String.valueOf(amount);
        //设置订单金额
        data.put("total_fee","1");
        data.put("notify_url","http://www.example.com/wxpay/notify");
        //设置支付方式
        data.put("trade_type", "NATIVE");
        //统一下单
        Map<String, String> resp = wxpay.unifiedOrder(data);
        //这一块必须用log4j做记录
        System.out.println(orderId+"下订单结果为："+ JSONObject.toJSONString(resp));
        if ("SUCCESS".equalsIgnoreCase(resp.get("return_code"))&&"SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
            rs.put("code",200);
            rs.put("url",resp.get("code_url"));
            //更新订单状态
            order.setPayStatus(PayStatusEnum.PAY_STATUS_ING.getStatus());
            orderMapper.updateById(order);
        }
        return rs;
    }

    @Override
    public Integer queryPayStatus(Integer orderId) throws Exception {
        FeiConfig config = new FeiConfig();
        WXPay wxpay = new WXPay(config);
        Map<String,String> data = new HashMap<>();
        data.put("out_trade_no","weixin1_order_lsc_"+orderId);
        //查询支付状态
        Map<String,String> resp = wxpay.orderQuery(data);
        System.out.println("查询结果："+JSONObject.toJSONString(resp));
        if ("SUCCESS".equalsIgnoreCase(resp.get("return_code"))&&"SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
            if ("SUCCESS".equalsIgnoreCase(resp.get("trade_state"))){//支付成功
                //更新订单状态
                Order order = new Order();
                order.setId(orderId);
                order.setPayStatus(PayStatusEnum.PAY_STATUS_SUCCESS.getStatus());
                orderMapper.updateById(order);
                return 1;
            }else if ("NOTPAY".equalsIgnoreCase(resp.get("trade_state"))){
                return 3;
            }else if ("USERPAYING".equalsIgnoreCase(resp.get("trade_state"))){
                return 2;
            }
        }
        return 0;
    }


}
