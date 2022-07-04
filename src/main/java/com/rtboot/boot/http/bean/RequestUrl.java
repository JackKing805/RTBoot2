package com.rtboot.boot.http.bean;

import com.rtboot.boot.http.utils.StringParser;
import com.rtboot.boot.rtboot.utils.Logger;

import java.util.HashMap;
import java.util.Map;

public class RequestUrl {
    private final String url;
//    private String domain;//域名
    private String path;//请求路径
    private Map<String,String> map;//请求参数
//    private String pathValue;//路径参数

    public RequestUrl(String url) {
        this.url = url;
        parseUrl();
    }

    private void parseUrl(){
        map = new HashMap<>();
        if(url.isEmpty()){
            path = "";
            return;
        }
        String[] split = url.split("[?]");
        if (split.length>0){
            path = split[0];
            if (split.length>1){
                String suffix = split[1];
                String[] kv = suffix.split("&");
                for (String paramKV : kv) {
                    if (paramKV.contains("=")){
                        String[] realKv = paramKV.split("=");
                        String[] result = StringParser.parseStringKV(realKv);
                        if (result!=null){
                            map.put(result[0],result[1]);
                        }
                    }
                }
            }
        }
    }


    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getMap() {
        return map;
    }

    @Override
    public String toString() {
        return "RequestUrl{" +
                "url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", map=" + map +
                '}';
    }
}
