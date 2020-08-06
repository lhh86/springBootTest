package com.fh.controller;

import com.fh.common.jsonDate;
import com.fh.utils.JWT;
import com.fh.utils.RedisUse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("VipController")
public class VipController {

    @RequestMapping("sendMessage")
    public jsonDate sendMessage(String iphone){
        //发送短信   阿里提供的短信服务
        /*String s = MessageUtils.sendMsg(iphone);*/
        String code = "1234";//固定验证码  不发送短信
        //存到redis中
        RedisUse.set(iphone,code,60*5);
        return jsonDate.getJsonSuccess("短信发送成功");
    }
    //登录
    @RequestMapping("login")
    public jsonDate login(String iphone,String code){
        Map logMap = new HashMap();
        //
        String redis_code = RedisUse.get(iphone);
        if (redis_code!=null && redis_code.equals(code)){
            //生成一个秘钥
            Map user = new HashMap();
            user.put("iphone",iphone);
            String sign = JWT.sign(user, 1000 * 60 * 60 * 24);
            //加签 base64  手机号+sign
            String token = Base64.getEncoder().encodeToString((iphone + "," + sign).getBytes());

            RedisUse.set("token_"+iphone,sign,60*30);
            logMap.put("status","200");
            logMap.put("message","登陆成功");
            //签名
            logMap.put("token",token);
        }else {
            logMap.put("status","300");
            logMap.put("message","用户名不存在 或验证码错误");
        }
        return jsonDate.getJsonSuccess(logMap);
    }
}

