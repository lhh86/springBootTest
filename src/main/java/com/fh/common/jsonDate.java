package com.fh.common;

public class jsonDate {

    private Integer code;

    private  String message;

    private Object data;


    private jsonDate(Object data){
        this.data=data;
        this.code=200;
        this.message="success";
    }

    //失败
    private jsonDate(String message){
        this.code=500;
        this.message="message";
    }

    private jsonDate(Integer code, String message){
        this.code=code;
        this.message=message;
    }

    //成功
    public static jsonDate getJsonSuccess(Object data){
        return new jsonDate(data);
    }

    public static jsonDate getJsonError(Integer code, String message){
        System.out.println(message);
        return new jsonDate(code,message);
    }


    public static jsonDate getJsonError(String message){
        System.out.println(message);
        return new jsonDate(message);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
