package com.fh;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("TestController")
public class TestController {

    @RequestMapping("test")
    public String test(){
        System.out.println("hello work");
        return "进来了";
    }
}
