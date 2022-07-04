package com.rtboot.boot.http.model;

import com.rtboot.boot.http.bean.RequestHeader;
import com.rtboot.boot.http.bean.RequestUrl;
import com.rtboot.boot.http.enums.RequestProtocol;
import com.rtboot.boot.http.enums.RequestVersion;

public class Request {
    private final RequestProtocol requestProtocol;
    private final RequestVersion requestVersion;
    private final RequestUrl requestUrl;
    private final RequestHeader requestHeader;

    public Request(RequestProtocol requestProtocol, RequestVersion requestVersion, RequestUrl requestUrl, RequestHeader requestHeader) {
        this.requestProtocol = requestProtocol;
        this.requestVersion = requestVersion;
        this.requestUrl = requestUrl;
        this.requestHeader = requestHeader;
    }


    public RequestProtocol getRequestProtocol() {
        return requestProtocol;
    }

    public RequestVersion getRequestVersion() {
        return requestVersion;
    }

    public RequestUrl getRequestUrl() {
        return requestUrl;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestProtocol=" + requestProtocol +
                ", requestVersion=" + requestVersion +
                ", requestUrl=" + requestUrl +
                ", requestHeader=" + requestHeader +
                '}';
    }
}
