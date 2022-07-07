package com.rtboot.boot.http.bean;


import java.net.MalformedURLException;
import java.net.URL;

public class RequestUrl {
    private final URL url;

    public RequestUrl(String protocol,String host,int port,String url) throws MalformedURLException {
        this.url = new URL(protocol,host,port,url);
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "protocol:"+url.getProtocol()+",host:"+url.getHost()+",port:"+url.getPort()+",defaultPort:"+url.getDefaultPort()+",path:"+url.getPath()+",ref:"+url.getRef()+",query:"+url.getQuery()+",file:"+url.getFile();
    }
}
