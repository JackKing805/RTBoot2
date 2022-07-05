package com.rtboot.boot.http.handler.i;

import com.rtboot.boot.http.handler.model.RequestResult;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;

public abstract class RequestHandler {
    private RequestHandler next;


    protected abstract RequestResult handlerRequest(RtContext rtContext, RtRequest rtRequest, RtResponse rtResponse) throws Exception;

    public RequestResult handleNext(RtContext rtContext, RtRequest rtRequest,RtResponse rtResponse){
        RequestResult requestResult;
        try {
            requestResult = handlerRequest(rtContext, rtRequest,rtResponse);
        } catch (Exception e) {
            return RequestResult.failure(new ResponseMessage(505,"server content error",null));
        }
        if (RequestResult.isNext(requestResult)){
            if (next!=null){
                return next.handleNext(rtContext, rtRequest,rtResponse);
            }else {
                return RequestResult.failure(new ResponseMessage(400,"bad request",null));
            }
        }else {
            return requestResult;
        }
    }

    public void setNext(RequestHandler requestHandler){
        next = requestHandler;
    }

    public static class Builder{
        private RequestHandler header;
        private RequestHandler tail;

        public Builder addHandler(RequestHandler requestHandler){
            if(header==null){
                header = tail = requestHandler;
                return this;
            }
            tail.setNext(requestHandler);
            tail = requestHandler;
            return this;
        }

        public RequestHandler build(){
            if (header==null){
                throw new NullPointerException("must set header handler");
            }
            return header;
        }
    }
}
