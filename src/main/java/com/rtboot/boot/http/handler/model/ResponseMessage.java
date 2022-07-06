package com.rtboot.boot.http.handler.model;


public class ResponseMessage {
    private final int code;
    private final String msg;
    private final Object content;

    public ResponseMessage(int code, String msg, Object content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getContent() {
        return content;
    }
}
