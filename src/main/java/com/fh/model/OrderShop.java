package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("shop_order_shop")
public class OrderShop {
    @TableId(value = "id",type = IdType.AUTO)
    private  Integer id;
    @TableField("orderId")
    private Integer orderId;
    @TableField("shopductId")
    private Integer shopductId;
    @TableField("allCount")
    private Integer allCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getShopductId() {
        return shopductId;
    }

    public void setShopductId(Integer shopductId) {
        this.shopductId = shopductId;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }
}
