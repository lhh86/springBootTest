package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.Shop;
import com.fh.model.ShopCar;
import com.fh.model.ShopInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopMapper extends BaseMapper<Shop> {
    List<Shop> queryShopData();

    ShopInfo queryShopById(Integer id);

    List<Shop> queryShopAll(@Param("typeId") Integer typeId);

    public ShopCar queryShopCarById(Integer pid);

    String queryAreaNameById(String areaId);

    int updateShopCount(@Param("id") Integer id,@Param("count") Integer count);
}