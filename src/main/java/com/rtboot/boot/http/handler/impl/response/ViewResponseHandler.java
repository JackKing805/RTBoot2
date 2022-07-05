package com.rtboot.boot.http.handler.impl.response;

import com.rtboot.boot.http.handler.i.ResponseHandler;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.handler.model.ResponseWrapper;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;


public class ViewResponseHandler extends ResponseHandler {
    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ResponseWrapper handlerResponse(RtContext rtContext, RtRequest rtRequest, RtResponse rtResponse, ResponseMessage responseMessage) {
        Logger.i("handlerResponse ViewResponseHandler");
        ResponseWrapper responseWrapper = new ResponseWrapper(rtRequest, responseMessage, outputStream -> outputStream.write(responseMessage.getContent().toString().getBytes()));
        responseWrapper.setContentType(getContentType());
        responseWrapper.setContentLength(responseMessage.getContentLength());
        return responseWrapper;
    }
}
