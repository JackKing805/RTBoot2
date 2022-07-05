package com.rtboot.boot.http.handler.impl.response;

import com.rtboot.boot.http.enums.ContentType;
import com.rtboot.boot.http.handler.i.ResponseHandler;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.handler.model.ResponseWrapper;
import com.rtboot.boot.http.i.ResponseWriteListener;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.Map;

public class StringResponseHandler extends ResponseHandler {
    @Override
    public String getContentType() {
        return ContentType.TEXT_PLAIN.getContentType();
    }

    @Override
    public ResponseWrapper handlerResponse(RtContext rtContext, RtRequest rtRequest, RtResponse rtResponse, ResponseMessage responseMessage) {
        Logger.i("handlerResponse StringResponseHandler");
        ResponseWrapper responseWrapper = new ResponseWrapper(rtRequest, responseMessage, outputStream -> outputStream.write(responseMessage.getContent().toString().getBytes()));
        responseWrapper.setContentType(getContentType());
        responseWrapper.setContentLength(responseMessage.getContentLength());
        return responseWrapper;
    }
}
