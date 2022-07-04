package com.rtboot.boot.http.handler.impl.request;

import com.rtboot.boot.http.handler.i.RequestHandler;
import com.rtboot.boot.http.model.Request;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;

public class RootRequestHandler extends RequestHandler {
    @Override
    protected boolean handlerRequest(RtContext rtContext, Request request) {
        if (request.getRequestUrl().getPath().equals("/")){
            Logger.i("rootPath request:"+request);
            return false;
        }
        return true;
    }
}
