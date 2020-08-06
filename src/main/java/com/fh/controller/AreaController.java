package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.jsonDate;
import com.fh.model.Area;
import com.fh.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController
@RequestMapping("AreaController")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @RequestMapping("queryAreaAll")
    @CrossOrigin
    public jsonDate queryAreaAll(){
        Jedis jedis = new Jedis("192.168.116.135");
        return jsonDate.getJsonSuccess(jedis.get("area_lsc"));

    }

    @RequestMapping("reflusRedis")
    public jsonDate reflusRedis(){
        List<Area> areas = areaService.queryAreaAll();
        String jsonString = JSONObject.toJSONString(areas);
        //放入到redis中
        Jedis jedis = new Jedis("192.168.116.135");
        jedis.set("area_lsc",jsonString);
        jedis.close();
        return jsonDate.getJsonSuccess("数据刷新成功");
    }
}
