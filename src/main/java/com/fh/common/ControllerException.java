package com.fh.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public jsonDate test(){
        return jsonDate.getJsonError("参数为 0 ");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public  jsonDate handleException(Exception e){
        return jsonDate.getJsonError(e.getMessage());
    }


    @ExceptionHandler(NologinException.class)
    @ResponseBody
    public jsonDate handleNoLoginException(NologinException e){
        return jsonDate.getJsonError(1000,e.getMessage());
    }

    @ExceptionHandler(CountException.class) //
    @ResponseBody
    public jsonDate handleCountException(CountException e){
        return jsonDate.getJsonError(2000,e.getMessage());
    }
}
