package com.fh.controller;



import com.alibaba.fastjson.JSONObject;
import com.fh.common.jsonDate;
import com.fh.model.Type;
import com.fh.service.TypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;
@RestController
@RequestMapping("TypeController")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping("queryTypeAll")
    @CrossOrigin
    public jsonDate queryTypeAll(){
        Jedis jedis = new Jedis("192.168.116.135");
        return jsonDate.getJsonSuccess(jedis.get("type_json_lsc"));
    }


    @RequestMapping("reflusRedis")
    public jsonDate reflusRedis(){
        List<Type> types = typeService.queryTypeAll();
        String jsonString = JSONObject.toJSONString(types);
        //放入到redis中
        Jedis jedis = new Jedis("192.168.116.135");
        jedis.set("type_json_lsc",jsonString);
        jedis.close();
        return jsonDate.getJsonSuccess("redis刷新成功");
    }
}
