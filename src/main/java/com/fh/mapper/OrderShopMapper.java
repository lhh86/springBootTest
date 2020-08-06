package com.fh.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.OrderShop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderShopMapper extends BaseMapper<OrderShop> {

    public void batchAdd(@Param("list") List<OrderShop> list, @Param("oid") Integer oid);
}
