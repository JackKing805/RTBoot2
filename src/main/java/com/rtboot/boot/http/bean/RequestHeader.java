package com.rtboot.boot.http.bean;

import java.util.Map;

public class RequestHeader {
    private final Map<String,Object> parameters;

    public RequestHeader(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Map<String,Object> getParameters(){
        return parameters;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "parameters=" + parameters +
                '}';
    }
}
