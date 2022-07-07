package com.rtboot.boot.http.handler.impl.response;

import com.rtboot.boot.http.handler.i.ResponseHandler;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.handler.model.ResponseWrapper;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;

public class ErrorResponseHandler extends ResponseHandler {
    @Override
    public ResponseWrapper handlerResponse(RtContext rtContext, RtRequest rtRequest, RtResponse rtResponse, ResponseMessage responseMessage) {
        Logger.i("ErrorResponseHandler handlerResponse");
        String content = (String) responseMessage.getContent();
        ResponseWrapper responseWrapper = new ResponseWrapper(rtRequest, responseMessage, outputStream -> outputStream.write(((String) responseMessage.getContent()).getBytes()));
        responseWrapper.setContentType(getContentType(rtRequest));
        responseWrapper.setContentLength(content.length());
        return responseWrapper;
    }
}
