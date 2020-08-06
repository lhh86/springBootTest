package com.fh.common.config;


import com.fh.common.interceptor.KuaYuInterceptor;
import com.fh.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//拦截器的配置   拦截什么 不拦什么
@Configuration   //声明是个配置文件类
public class interceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器   跨域拦截器
        InterceptorRegistration kyregistration = registry.addInterceptor(new KuaYuInterceptor());
        kyregistration.addPathPatterns("/**");

        //注册TestInterceptor拦截器   跨域拦截器
        InterceptorRegistration dlregistration = registry.addInterceptor(new LoginInterceptor());
        dlregistration.addPathPatterns("/CarController/**");
        dlregistration.addPathPatterns("/AddareaController/**");
        dlregistration.addPathPatterns("/OrderController/**");
    }
}
