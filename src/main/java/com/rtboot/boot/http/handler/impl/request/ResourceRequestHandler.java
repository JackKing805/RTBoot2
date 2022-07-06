package com.rtboot.boot.http.handler.impl.request;

import com.rtboot.boot.http.handler.i.RequestHandler;
import com.rtboot.boot.http.handler.model.RequestResult;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;

import java.net.URL;
import java.util.Objects;
import java.util.regex.Pattern;

public class ResourceRequestHandler extends RequestHandler {
    @Override
    protected RequestResult handlerRequest(RtContext rtContext, RtRequest rtRequest, RtResponse rtResponse) {
        if (rtRequest.getRequestUrl().getResourceName()!=null){
            Logger.i("ResourceRequestHandler handlerRequest:"+rtRequest.getRequestUrl().getUrl());
            String resourceName = rtRequest.getRequestUrl().getResourceName();
            if (resourceName.equals("favicon.ico")){
                resourceName = rtContext.getStringProperties("http.resources.favicon", "favicon.ico");
            }else {
                String host = rtRequest.getRequestHeader().getHost().trim();
                String referer = rtRequest.getRequestHeader().getReferer().trim();
                String removeNameReferer = referer.replace("https//" +host+"/","").replace("http//" +host+"/","");
                String[] split = removeNameReferer.split("/");
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < split.length-1; i++) {
                    result.append("/").append(split[i]);
                }
                resourceName = rtRequest.getRequestUrl().getUrl().replace(result,"");
            }


            Logger.i("ResourceRequestHandler resourceName:"+resourceName);


            String prefix = rtContext.getStringProperties("http.resources.prefix", "static");
            URL resources = rtContext.getResources(prefix+ "/"+resourceName);
            return RequestResult.success(new ResponseMessage(200,"ok",resources));
        }
        return RequestResult.next();
    }
}
