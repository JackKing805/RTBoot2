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

    private static ResponseMessage buildHtml(int code,String msg,String title,String content){
        return new ResponseMessage(code,msg,"<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>"+title+"</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h5>"+content+"</h5>\n" +
                "</body>\n" +
                "</html>");
    }

    public static ResponseMessage _400(){
        return buildHtml(400,"bad request","400","bad request");
    }
    public static ResponseMessage _404(){
        return buildHtml(404,"not found page","404","not found page");
    }

    public static ResponseMessage _505(){
        return buildHtml(505,"server error","505","server error");
    }
}
