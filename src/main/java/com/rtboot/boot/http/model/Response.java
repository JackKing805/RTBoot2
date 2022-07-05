package com.rtboot.boot.http.model;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public class Response implements Serializable {
    private final int code;
    private final String msg;
    @Nullable
    private final Object data;

    public Response(int code, String msg,@Nullable Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Nullable
    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getContentLength(){
        return toString().length();
    }

    public static Response unknownRequest(){
        return new Response(404,"未知的请求",null);
    }

    public static Response badRequest(){
        return new Response(400,"请求错误",null);
    }

    public static Response errorRequest(int code,String msg){
        return new Response(code,msg,null);
    }

    public static Response successRequest(int code,String msg,@Nullable Object object){
        return new Response(code,msg,object);
    }
}
