package com.fh.controller;

import com.fh.common.CountException;
import com.fh.common.jsonDate;
import com.fh.service.OrderService;
import com.fh.utils.RedisUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("OrderController")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //生成订单  收货地址    支付方式
    @RequestMapping("createOrder")
    public jsonDate createOrder(Integer addareaId,Integer payType,String flag) throws CountException {
        //处理接口的幂等性，  同一个请求发送多次和一次是一样的  结果只处理一次
        boolean exists = RedisUse.exists(flag);
        if (exists==true){
            return  jsonDate.getJsonError(300,"请求处理中");
        }else {
            RedisUse.set(flag,"",10);
        }
        Map map = orderService.createOrder(addareaId,payType);
        return jsonDate.getJsonSuccess(map);
    }


    //生成二维码  有效时间30分钟
    @RequestMapping("createMeonyPhoto")
    public jsonDate createMeonyPhoto(Integer orderId) throws Exception {
       Map meonyPhoto= orderService.createMeonyPhoto(orderId);
       return jsonDate.getJsonSuccess(meonyPhoto);
    }

    //状态  1 支付成功 2 支付中  3 未支付
    @RequestMapping("queryPayStatus")
    public jsonDate queryPayStatus(Integer orderId) throws Exception {
        Integer status = orderService.queryPayStatus(orderId);
        return jsonDate.getJsonSuccess(status);
    }
}
