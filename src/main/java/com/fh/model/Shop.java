package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@TableName("t_shop")
public class Shop {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("name")
    private String name;//名称
    @TableField("price")
    private Double price;//价格
    @TableField("isup")
    private Integer isup;//是否上架
    @TableField("createDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;//出厂日期
    @TableField("rexiao")
    private Integer rexiao;//是否热销
    @TableField("img")
    private String img;///图片

    //品牌
    @TableField("brandId")
    private Integer brandId;

    //地区
    @TableField("areaId")
    private String areaId;

    //类型
    @TableField("typeId")
    private String typeId;

    //库存
    @TableField("storyCount")
    private Integer storyCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getIsup() {
        return isup;
    }

    public void setIsup(Integer isup) {
        this.isup = isup;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getRexiao() {
        return rexiao;
    }

    public void setRexiao(Integer rexiao) {
        this.rexiao = rexiao;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getStoryCount() {
        return storyCount;
    }

    public void setStoryCount(Integer storyCount) {
        this.storyCount = storyCount;
    }
}
