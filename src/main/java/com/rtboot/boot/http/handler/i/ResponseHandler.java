package com.rtboot.boot.http.handler.i;

import com.rtboot.boot.http.enums.ContentType;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.handler.model.ResponseWrapper;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;

import java.io.FileNotFoundException;

public abstract class ResponseHandler {
    public  String getContentType(RtRequest rtRequest){
        String[] accept = rtRequest.getRequestHeader().getAccept();
        if (accept!=null && accept.length>0){
            return accept[0];
        }else {
            return ContentType.TEXT_PLAIN.getContentType();
        }
    }
    /**
     * false 继续执行，true 中断执行
     */
    public abstract ResponseWrapper handlerResponse(RtContext rtContext,RtRequest rtRequest, RtResponse rtResponse, ResponseMessage responseMessage);


}
