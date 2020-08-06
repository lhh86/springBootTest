package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.fh.mapper.ShopMapper;
import com.fh.model.Shop;
import com.fh.model.ShopCar;
import com.fh.service.CarService;
import com.fh.utils.RedisUse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CarServiceImpl implements CarService {


    @Resource
    private ShopMapper shopMapper;
    @Resource
    private HttpServletRequest request;

    @Override                     //商品id         //购买商品数量
    public Integer addShopCar(Integer shopId, Integer count) {
        //从request 中取出用户信息   存到redis中
        Map login_user = (Map) request.getAttribute("login_user");
        String  iphone = (String) login_user.get("iphone");
        //获取购物车中指定 商品的信息
        String shopInfo = RedisUse.hget("CarController_" + iphone, shopId + "");


        if (count>0){
            Shop shop = shopMapper.selectById(shopId);
            //判断库存是否足够
            if (count>shop.getStoryCount()){
                return shop.getStoryCount()-count;
            }
        }

        //判断商品是否存在购物车中  redis  是否为空
        if (StringUtils.isEmpty(shopInfo)){
            //为空,通过商品id去数据库查询商品  从dao层查询  queryShopCarById
            ShopCar shopCar = shopMapper.queryShopCarById(shopId);
            shopCar.setCheck(true);
            shopCar.setCount(count);
            //计算小计 总金额
            BigDecimal money = shopCar.getPrice().multiply(new BigDecimal(count));
            shopCar.setMoney(money);
            //将商品信息转为json字符串
            String shopCarString = JSONObject.toJSONString(shopCar);
            //把数据放到redis中
            RedisUse.hset("CarController_"+iphone,shopId+"",shopCarString);
        }
        else
            {//存在购物车
            //把数据从redis中取出    把json字符串转为java对象
            ShopCar shopCar = JSONObject.parseObject(shopInfo, ShopCar.class);
            //修改个数
            shopCar.setCount(shopCar.getCount()+count);
            //判断库存是否足够   验证库存
            Shop shop = shopMapper.selectById(shopId);
            //购物车总数量   小于    库存
            if (shopCar.getCount()>shop.getStoryCount()){
                return shop.getStoryCount()-shopCar.getCount();
            }
            //计算小节
            BigDecimal money = shopCar.getPrice().multiply(new BigDecimal(shopCar.getCount()));
            shopCar.setMoney(money);
            //将商品信息  转为json字符串
            String shopCarString = JSONObject.toJSONString(shopCar);
            //将数据放到redis中
            RedisUse.hset("CarController_"+iphone,shopId+"",shopCarString);

        }
        //怎么获取商品种类的个数
        long hlen = RedisUse.hlen("CarController_" + iphone);
        return (int)hlen;
    }

    @Override
    public List queryCarByUser() {
        //查询当前用户所有购物车 的数据
        //获取购物车的所有数据
        Map login_user = (Map) request.getAttribute("login_user");
        String  iphone = (String) login_user.get("iphone");
        //获取购物车 的所有数据
        List<String> shopCar = RedisUse.hvals("CarController_" + iphone);
        return shopCar;
    }
    //修改购物车商品的状态
    @Override
    public void updataCarShopStatus(String sid) {
        //查询当前用户的购物车数据
        //查询用户信息
        Map login_user = (Map) request.getAttribute("login_user");
        String iphone = (String) login_user.get("iphone");

        //获取购物车的所有数据
        List<String> shopCars = RedisUse.hvals("CarController_" + iphone);
        for (int i = 0; i <shopCars.size() ; i++) {
            //商品的json字符串
            String shopStr = shopCars.get(i);
            //把购物车的商品信息转为字符串
            ShopCar shopCar = JSONObject.parseObject(shopStr, ShopCar.class);
            //判断该商品的是否要修改选中的状态
            Integer id = shopCar.getId();
            //判断此商品是否在选中的ids中
            if ((","+sid).contains(","+id+",")!=true){
                shopCar.setCheck(false);
                RedisUse.hset("car_"+iphone,shopCar.getId()+"",JSONObject.toJSONString(shopCar));
            }
        }
    }

    @Override
    public List<ShopCar> queryCheckShop() {
        //从redis中取出购物车数据 返回
        //获取登录信息  用户信息
        Map login_user = (Map) request.getAttribute("login_user");
        String  iphone = (String) login_user.get("iphone");

        //获取购物车数据   获取购物车的所有数据
        List<String> shopCars = RedisUse.hvals("CarController_"+iphone);

        //实际需求的数据
        List<ShopCar> list = new ArrayList<>();
        //处理想要的数据
        for (int i = 0; i <shopCars.size() ; i++) {
            //购物车的每一件商品信息
            String shopCarStr = shopCars.get(i);
            //将字符串转为bean
            ShopCar shopCar = JSONObject.parseObject(shopCarStr, ShopCar.class);
            //想要的选中的数据
            if(shopCar.isCheck()==true){
                list.add(shopCar);
            }
        }
        return list;
    }


    @Override
    public void deleteShopCar(HttpServletRequest request, Integer id) {
        Map login_user = (Map) request.getAttribute("login_user");
        String  iphone = (String) login_user.get("iphone");
        RedisUse.hdel("CarController_" + iphone,id+"");
    }

    @Override
    public void deleteShopCartAll(List<Integer> list) {
         Map login_user = (Map) request.getAttribute("login_user");
        String  iphone = (String) login_user.get("iphone");
        for (int i = 0; i < list.size(); i++) {
            Integer shopId = list.get(i);
            RedisUse.hdel("CarController_" + iphone, shopId + "");
        }
    }

}

