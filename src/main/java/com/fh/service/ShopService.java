package com.fh.service;

import com.fh.model.Shop;
import com.fh.model.ShopInfo;

import java.util.List;

public interface ShopService {
    List<Shop> queryShopAll(Integer typeId);

    ShopInfo queryShopById(Integer id);

    /*List<ShopInfo> queryHotShopData();*/
}
