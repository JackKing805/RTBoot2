package com.rtboot.boot.http.handler.model;

import com.rtboot.boot.http.enums.RequestProtocol;
import com.rtboot.boot.http.enums.RequestVersion;
import com.rtboot.boot.http.i.ResponseWriteListener;
import com.rtboot.boot.http.model.RtRequest;

import java.util.HashMap;
import java.util.Map;

public class ResponseWrapper {
    private final RequestProtocol requestProtocol;
    private final RequestVersion requestVersion;
    private final ResponseMessage responseMessage;
    private final Map<String,Object> header = new HashMap<>();

    private ResponseWriteListener responseWriteListener;

    public ResponseWrapper(RtRequest request, ResponseMessage responseMessage,ResponseWriteListener responseWriteListener) {
        this.requestProtocol = request.getRequestProtocol();
        this.requestVersion = request.getRequestVersion();
        this.responseMessage = responseMessage;
        this.responseWriteListener = responseWriteListener;
    }


    public RequestProtocol getRequestProtocol() {
        return requestProtocol;
    }

    public RequestVersion getRequestVersion() {
        return requestVersion;
    }

    public ResponseMessage getResponseMessage() {
        return responseMessage;
    }

    public Map<String, Object> getHeader() {
        return header;
    }


    public ResponseWriteListener getResponseWriteListener() {
        return responseWriteListener;
    }

    public void addHeader(String key, Object value){
        header.put(key,value);
    }

    public void setContentType(String contentType){
        addHeader("Content-Type:",contentType);
    }

    public void setContentLength(int contentLength){
        addHeader("Content-Length:",contentLength);
    }

}
