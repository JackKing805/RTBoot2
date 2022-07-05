package com.rtboot.boot.http.handler.impl.request;

import com.rtboot.boot.http.handler.i.RequestHandler;
import com.rtboot.boot.http.handler.model.RequestResult;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;

import java.net.URL;

public class ResourceRequestHandler extends RequestHandler {
    @Override
    protected RequestResult handlerRequest(RtContext rtContext, RtRequest rtRequest, RtResponse rtResponse) {
        if (rtRequest.getRequestUrl().getPath().equals("/favicon.ico")){
            URL resources = rtContext.getResources("favicon.ico");
            return RequestResult.success(new ResponseMessage(200,"ok",resources));
        }
        return RequestResult.next();
    }
}
