package com.rtboot.boot.http.handler.impl.response;

import com.rtboot.boot.http.handler.i.ResponseHandler;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.handler.model.ResponseWrapper;
import com.rtboot.boot.http.i.ResponseWriteListener;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;

import java.io.OutputStream;
import java.net.URL;

public class URLResponseHandler extends ResponseHandler {
    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ResponseWrapper handlerResponse(RtContext rtContext, RtRequest rtRequest, RtResponse rtResponse, ResponseMessage responseMessage) {
        Logger.i("handlerResponse URLResponseHandler");
        ResponseWrapper responseWrapper = new ResponseWrapper(rtRequest, responseMessage, new ResponseWriteListener() {
            @Override
            public void write(OutputStream outputStream) {

            }
        });
        responseWrapper.setContentType(getContentType());
        responseWrapper.setContentLength(responseMessage.getContentLength());
        return responseWrapper;
    }
}
