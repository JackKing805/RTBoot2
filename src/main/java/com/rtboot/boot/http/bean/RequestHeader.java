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

    public <T> T getParameters(String key){
        Object value = parameters.get(key);
        if (value!=null){
            return (T) value;
        }else {
            return null;
        }
    }

    public String[] getAccept(){
        String accept = getParameters("accept");
        if (accept!=null){
            String[] split = accept.trim().split(";");
            if (split.length>0){
                return split[0].split(",");
            }
        }
        return new String[]{""};
    }

    public String getUserAgent(){
        return getParameters("user-agent");
    }

    public String getSecFetchDest(){
        return getParameters("sec-fetch-dest");
    }
    public String getHost(){
        return getParameters("host");
    }


    public String getReferer(){
        return  getParameters("referer");
    }
    @Override
    public String toString() {
        return "RequestHeader{" +
                "parameters=" + parameters +
                '}';
    }
}
