package com.rtboot.boot.http.handler.impl.request;

import com.rtboot.boot.http.handler.i.RequestHandler;
import com.rtboot.boot.http.handler.model.RequestResult;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;

public class ErrorRequestHandler extends RequestHandler {
    @Override
    protected RequestResult handlerRequest(RtContext rtContext, RtRequest rtRequest, RtResponse rtResponse) throws Exception {
        Logger.e("ErrorRequestHandler request:"+ rtRequest);
        return RequestResult.failure(ResponseMessage._404());
    }
}
