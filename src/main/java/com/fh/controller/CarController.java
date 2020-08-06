package com.fh.controller;

import com.fh.common.jsonDate;
import com.fh.model.ShopCar;
import com.fh.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("CarController")
public class CarController {

    @Autowired
    private CarService carService;

    //添加商品到购物车
    @RequestMapping("addCar")
    public jsonDate addCar(@RequestParam("id") Integer shopId, Integer count){
        Integer count_type = carService.addShopCar(shopId, count);
        return  jsonDate.getJsonSuccess(count_type);
    }

    @RequestMapping("queryCarByUser")
    public jsonDate queryCarByUser(){
       List list = carService.queryCarByUser();
       return jsonDate.getJsonSuccess(list);
    }

    //更新购物车中商品的状态
    @RequestMapping("updataCarShopStatus")
    public jsonDate updataCarShopStatus(String sid){
        carService.updataCarShopStatus(sid);
        return jsonDate.getJsonSuccess("修改成功");
    }
    // 查询购物车中要结算的商品信息    返回的数据格式 [{"id":id,}]
    @RequestMapping("queryCheckShop")
    public jsonDate queryCheckShop(){
        List<ShopCar>shopCar = carService.queryCheckShop();
        return jsonDate.getJsonSuccess(shopCar);
    }

    @RequestMapping("deleteShopCar")
    @ResponseBody
    public jsonDate deleteShopCar(HttpServletRequest request, Integer id){
        carService.deleteShopCar(request,id);
        return jsonDate.getJsonSuccess("删除成功");
    }

    @RequestMapping("deleteShopCartAll")
    @ResponseBody
    public jsonDate deleteShopCartAll(@RequestParam("ids[]")List<Integer> list) {
        carService.deleteShopCartAll(list);
        return jsonDate.getJsonSuccess("删除成功");
    }
}
