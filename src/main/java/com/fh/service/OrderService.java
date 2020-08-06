package com.fh.service;

import com.fh.common.CountException;

import java.util.Map;

public interface OrderService {
    Map createOrder(Integer addareaId, Integer payType) throws CountException;

    Map createMeonyPhoto(Integer orderId) throws Exception;

    Integer queryPayStatus(Integer orderId) throws Exception;
}
