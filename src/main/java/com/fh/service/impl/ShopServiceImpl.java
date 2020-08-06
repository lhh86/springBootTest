package com.fh.service.impl;

import com.fh.mapper.ShopMapper;
import com.fh.model.Shop;
import com.fh.model.ShopInfo;
import com.fh.service.ShopService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    @Resource
    private ShopMapper shopMapper;

    @Override
    public List<Shop> queryShopAll(Integer typeId) {
        return shopMapper.queryShopAll(typeId);
    }

    @Override
    public ShopInfo queryShopById(Integer id) {
        return shopMapper.queryShopById(id);
    }

    /*@Override
    public List<ShopInfo> queryHotShopData() {

        return shopMapper.;
    }*/
}
