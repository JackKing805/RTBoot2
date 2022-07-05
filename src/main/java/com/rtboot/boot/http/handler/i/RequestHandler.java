package com.rtboot.boot.http.handler.i;

import com.rtboot.boot.http.core.RtClient;
import com.rtboot.boot.http.handler.i.model.RequestHandlerResponse;
import com.rtboot.boot.http.model.Request;
import com.rtboot.boot.http.model.Response;
import com.rtboot.boot.rtboot.core.RtContext;

public abstract class RequestHandler {
    private RequestHandler next;

    /**
     * true 继续执行，false 中断执行
     * @param request
     * @return
     */
    protected abstract RequestHandlerResponse handlerRequest(RtContext rtContext, Request request);

    public Response handleNext(RtContext rtContext, Request request){
        RequestHandlerResponse response = handlerRequest(rtContext,request);
        if (RequestHandlerResponse.isContinue(response)){
            if(next!=null){
                return next.handleNext(rtContext,request);
            }else {
                if (response.getResponse()==null){
                    return Response.unknownRequest();
                }
            }
        }
        if (response.getResponse()!=null){
            return response.getResponse();
        }

        return Response.badRequest();
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
