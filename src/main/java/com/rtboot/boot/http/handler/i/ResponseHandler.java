package com.rtboot.boot.http.handler.i;

import com.rtboot.boot.http.handler.model.RequestResult;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.handler.model.ResponseWrapper;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;

public abstract class ResponseHandler {
    public abstract String getContentType();
    /**
     * false 继续执行，true 中断执行
     */
    public abstract ResponseWrapper handlerResponse(RtContext rtContext,RtRequest rtRequest, RtResponse rtResponse, ResponseMessage responseMessage);


}
