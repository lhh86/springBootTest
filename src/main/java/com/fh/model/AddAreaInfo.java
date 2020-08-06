package com.fh.model;

public class AddAreaInfo {
    private Integer id;    //用户id

    private String name;  //用户名称

    private String iphone; //用户手机号

    private String detailAdd;//详细地址

    private String allArea;

    private Integer ischeck;   //状态  是否是默认地址

    private String areaIds;//省 市 县

    public String getAllArea() {
        return allArea;
    }

    public void setAllArea(String allArea) {
        this.allArea = allArea;
    }

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

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }



    public Integer getIscheck() {
        return ischeck;
    }

    public void setIscheck(Integer ischeck) {
        this.ischeck = ischeck;
    }

    public String getDetailAdd() {
        return detailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        this.detailAdd = detailAdd;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }
}
