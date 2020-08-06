package com.fh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication//声明他是个启动类
@MapperScan("com.fh.mapper")
public class App 
{
    public static void main( String[] args )
    {
        /*System.out.println( "Hello World!" );*/
        SpringApplication.run(App.class,args);
    }
}
