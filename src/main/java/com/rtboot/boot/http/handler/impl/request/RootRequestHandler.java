package com.rtboot.boot.http.handler.impl.request;

import com.rtboot.boot.http.handler.i.RequestHandler;
import com.rtboot.boot.http.handler.i.model.RequestHandlerResponse;
import com.rtboot.boot.http.model.Request;
import com.rtboot.boot.http.model.Response;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;

public class RootRequestHandler extends RequestHandler {
    @Override
    protected RequestHandlerResponse handlerRequest(RtContext rtContext, Request request) {
        if (request.getRequestUrl().getPath().equals("/")){
            Logger.i("rootPath request:"+request);
            return RequestHandlerResponse.success(Response.successRequest(200,"success","root request"));//todo 返回资源
        }
        return RequestHandlerResponse.continueNext();
    }
}
