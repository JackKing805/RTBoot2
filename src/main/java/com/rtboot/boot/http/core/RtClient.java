package com.rtboot.boot.http.core;

import com.rtboot.boot.http.extend.Processor;
import com.rtboot.boot.http.handler.i.RequestHandler;
import com.rtboot.boot.http.handler.i.ResponseHandler;
import com.rtboot.boot.http.handler.impl.request.ControllerRequestHandler;
import com.rtboot.boot.http.handler.impl.request.ResourceRequestHandler;
import com.rtboot.boot.http.handler.impl.request.RootRequestHandler;
import com.rtboot.boot.http.handler.impl.response.*;
import com.rtboot.boot.http.handler.impl.response.manager.ResponseHandlerManager;
import com.rtboot.boot.http.handler.model.RequestResult;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.handler.model.ResponseWrapper;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;

import java.net.Socket;

public class RtClient implements Runnable {
    private final RtContext rtContext;
    private final Socket socket;

    public RtClient(RtContext rtContext, Socket socket) {
        this.rtContext = rtContext;
        this.socket = socket;
    }


    @Override
    public void run() {
        RtRequest rtRequest = Processor.processRequest(socket);
        if (rtRequest == null) {
            Logger.e("client bad request");
            return;
        }
        Logger.i("client request:" + rtRequest);

        RtResponse rtResponse = new RtResponse(socket);
        RequestResult requestResult = new RequestHandler.Builder()
                .addHandler(new RootRequestHandler())
                .addHandler(new ResourceRequestHandler())
                .addHandler(new ControllerRequestHandler())
                .build()
                .handleNext(rtContext, rtRequest, rtResponse);

        ResponseMessage responseMessage = requestResult.getResponseMessage();
        ResponseWrapper responseWrapper = ResponseHandlerManager.handlerResponse(rtContext, responseMessage).handlerResponse(rtContext, rtRequest, rtResponse, responseMessage);
        Processor.processResponse(rtRequest,rtResponse, responseWrapper);
    }
}
