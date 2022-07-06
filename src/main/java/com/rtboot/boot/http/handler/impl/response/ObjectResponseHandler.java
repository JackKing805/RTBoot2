package com.rtboot.boot.http.handler.impl.response;

import com.google.gson.Gson;
import com.rtboot.boot.http.handler.i.ResponseHandler;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.handler.model.ResponseWrapper;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;

public class ObjectResponseHandler extends ResponseHandler {
    @Override
    public ResponseWrapper handlerResponse(RtContext rtContext, RtRequest rtRequest, RtResponse rtResponse, ResponseMessage responseMessage) {
        Logger.i("handlerResponse ObjectResponseHandler");
        String json = new Gson().toJson(responseMessage.getContent());
        ResponseWrapper responseWrapper = new ResponseWrapper(rtRequest, responseMessage, outputStream -> outputStream.write(json.getBytes()));
        responseWrapper.setContentType(getContentType(rtRequest));
        responseWrapper.setContentLength(json.length());
        return responseWrapper;
    }
}
