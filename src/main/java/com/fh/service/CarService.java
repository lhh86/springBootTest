package com.fh.service;

import com.fh.model.ShopCar;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CarService {
    Integer addShopCar(Integer shopId, Integer count);

    List queryCarByUser();

    void updataCarShopStatus(String sid);


    List<ShopCar> queryCheckShop();

    void deleteShopCar(HttpServletRequest request, Integer id);

    void deleteShopCartAll(List<Integer> list);
}
