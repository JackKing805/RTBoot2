package com.rtboot.boot.http.core;

import com.rtboot.boot.http.extend.Processor;
import com.rtboot.boot.http.handler.i.RequestHandler;
import com.rtboot.boot.http.handler.impl.request.ControllerRequestHandler;
import com.rtboot.boot.http.handler.impl.request.RootRequestHandler;
import com.rtboot.boot.http.model.Request;
import com.rtboot.boot.http.model.Response;
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
        Request request = Processor.processRequest(socket);
        if (request==null){
            Logger.e("client bad request");
            return;
        }
        Logger.i("client request:"+request);

        Response response = new RequestHandler.Builder()
                .addHandler(new RootRequestHandler())
                .addHandler(new ControllerRequestHandler())
                .build()
                .handleNext(rtContext, request);

        Processor.processResponse(socket,request,response);
    }
}
