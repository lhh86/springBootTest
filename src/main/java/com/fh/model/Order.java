package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

@TableName("shop_order")
public class Order {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("addareaId")
    private Integer addareaId;
    @TableField("payType")
    private Integer payType;
    @TableField("shopTypeCount")
    private Integer shopTypeCount;
    @TableField("totalMoney")
    private BigDecimal totalMoney;
    @TableField("payStatus")
    private Integer payStatus;
    @TableField("createDate")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAddareaId() {
        return addareaId;
    }

    public void setAddareaId(Integer addareaId) {
        this.addareaId = addareaId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getShopTypeCount() {
        return shopTypeCount;
    }

    public void setShopTypeCount(Integer shopTypeCount) {
        this.shopTypeCount = shopTypeCount;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
