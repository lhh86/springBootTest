package com.fh.common.interceptor;

import com.fh.common.NologinException;
import com.fh.utils.JWT;
import com.fh.utils.RedisUse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Map;

//登录的拦截器
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*Object login_user = request.getSession().getAttribute("login_user");*/
       //从header里取数据
        String token = request.getHeader("token");
        //验证头信息是不是完整
        if (StringUtils.isEmpty(token)){
            //记录请求路径  存到redis里
            String requestURI = request.getRequestURI();
            throw new NologinException("头信息不完整");
        }
        //解密    字节数组
        byte[] decode = Base64.getDecoder().decode(token);
        //得到一个字符串  字节数组转为字符串  字符串的格式：iPhone+“，”+sign
        String  signToken  = new String(decode);
        //判断是否被篡改
        String[] split = signToken.split(",");
        if(split.length!=2){
            throw new NologinException("token的格式不正确");
        }

        String iphone = split[0];
        //jwt的密钥
        String sign  =split[1];
        Map user = JWT.unsign(sign,Map.class);
        if (user==null){
            throw new NologinException("沒有登录");
            /* response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSONObject.toJSONString(jsonDate.getJsonError(1000,"没有登录")));*/
        }
        if (user!=null){//jwt验证过了
            String sign_redis = RedisUse.get("token_" + iphone);
            if (!sign.equals(sign_redis)){//验证秘钥是不是最新的
                //返回json字符串
                throw new NologinException("验证过期，请重新登录");
            }
        }
        //前面的逻辑验证过了以后   设置redis key值的有效时间   30
        RedisUse.set("token_"+user.get("iphone"),sign,60*30);
        //再将用户信息放到request中    方便后面需求处理
        request.setAttribute("login_user",user);
        return true;
    }
}
