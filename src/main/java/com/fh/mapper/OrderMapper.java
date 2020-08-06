package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.Order;

public interface OrderMapper extends BaseMapper<Order> {
    void insertOreder(Order order);
}
