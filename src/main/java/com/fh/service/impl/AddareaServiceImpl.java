package com.fh.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.fh.mapper.AddareaMapper;
import com.fh.model.AddAreaInfo;
import com.fh.model.Area;
import com.fh.service.AddareaService;
import com.fh.utils.RedisUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AddareaServiceImpl implements AddareaService {
    @Resource
    private AddareaMapper addareaMapper;
    @Autowired
    private HttpServletRequest request;
    @Override
    public List<AddAreaInfo> queryAddArea() {
        //获取当前登录的用户信息  手机号
        Map login_user = (Map) request.getAttribute("login_user");
        String iphone = (String) login_user.get("iphone");
        //将手机号放到list集合中
        List<AddAreaInfo>list = addareaMapper.queryAddArea(iphone);
        //从redis中去查询地区信息
        String areaName = RedisUse.getAreaNames("area_lsc");
        //获取地区信息
        List<Area> area = JSONArray.parseArray(areaName, Area.class);
        String areaIds = "";
        String areaNames ="";
        //for循环
        for (int i = 0; i <list.size() ; i++) {
            AddAreaInfo addAreaInfo = list.get(i);
            areaIds = addAreaInfo.getAreaIds();
            for (int j = 0; j <area.size() ; j++) {
                //判断数据库中的省市县是否包含地区的每一个id  包含就拼接
                if (areaIds.contains(","+area.get(j).getId()+",")==true){
                  //将地区的每一个id都拼接上  用，号分割
                  areaNames+=area.get(j).getName()+",";
                }
            }//将地区名称和地区的详情信息  赋给addareaInfo
            addAreaInfo.setAllArea(areaNames+addAreaInfo.getDetailAdd());
            areaIds="";
            areaNames="";
        }
        return list;
    }




}
