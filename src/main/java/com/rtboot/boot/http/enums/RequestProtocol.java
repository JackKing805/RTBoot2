package com.rtboot.boot.http.enums;

/**
 * 请求方法
 */
public enum RequestProtocol {
    GET("get"),
    POST("get");

    private final String protocol;
    RequestProtocol(String protocol){
        this.protocol = protocol;
    }

    public String getProtocol(){
        return protocol;
    }

    public boolean isEquals(String protocol){
        return this.protocol.equalsIgnoreCase(protocol);
    }

    @Override
    public String toString() {
        return "RequestProtocol{" +
                "protocol='" + protocol + '\'' +
                '}';
    }
}
