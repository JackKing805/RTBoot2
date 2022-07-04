package com.rtboot.boot.http.utils;

import com.rtboot.boot.http.enums.RequestProtocol;
import com.rtboot.boot.http.enums.RequestVersion;

public class StringParser {
    public static RequestProtocol parserRequestProtocol(String protocol){
        if(RequestProtocol.GET.isEquals(protocol)){
            return RequestProtocol.GET;
        }else if(RequestProtocol.POST.isEquals(protocol)){
            return RequestProtocol.POST;
        }else {
            return null;
        }
    }

    public static RequestVersion parserRequestVersion(String version){
        if(RequestVersion.HTTP1_1.isEquals(version)){
            return RequestVersion.HTTP1_1;
        }else if(RequestVersion.HTTP2_0.isEquals(version)){
            return RequestVersion.HTTP2_0;
        }else {
            return null;
        }
    }


    public static String[] parseStringKV(String[] sp){
        if(sp!=null){
            String k =null;
            StringBuilder v = null;
            if(sp.length>=1){
                k = sp[0].toLowerCase().trim();
                if(sp.length>1){
                    v = new StringBuilder("");
                    for (int i = 1; i < sp.length; i++) {
                        v.append(sp[i]);
                    }
                }
            }

            if(v==null){
                v = new StringBuilder("");
            }
            if(k==null){
                return null;
            }
            return new String[]{k, v.toString()};
        }
        return null;
    }


    public static Object[] parseObjectKV(Object[] sp){
        if(sp!=null){
            String k =null;
            Object v = null;
            if(sp.length>=2){
                k = sp[0].toString().toLowerCase().trim();
                v = sp[1];
            }

            if(k!=null && v!=null){
                return new Object[]{k, v};
            }else{
                return null;
            }
        }
        return null;
    }
}
