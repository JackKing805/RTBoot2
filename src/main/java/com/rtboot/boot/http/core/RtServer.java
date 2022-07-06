package com.rtboot.boot.http.core;

import com.rtboot.boot.http.factory.ControllerFactory;
import com.rtboot.boot.http.utils.ThreadAgentUtils;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RtServer{
    private final RtContext rtContext;
    public RtServer(RtContext rtContext){
        this.rtContext = rtContext;
    }

    public void launchServer(){
        int listenPort = rtContext.getIntProperties("http.port", 8080);
        int maxRequest = rtContext.getIntProperties("http.request.max", 100);
        ControllerFactory.prepareController(rtContext);
        new Thread(new CoreServer(rtContext,listenPort,maxRequest)).start();
    }

    static class CoreServer implements Runnable {
        private final RtContext rtContext;
        private final int listenPort;
        private final int maxRequestNum;

        private int requestCount = 0;
        public CoreServer(RtContext rtContext,int listenPort,int maxRequestNum) {
            this.rtContext = rtContext;
            this.listenPort = listenPort;
            this.maxRequestNum = maxRequestNum;
        }

        @Override
        public void run() {
            Logger.i("server start,listen port:"+listenPort);
            try(ServerSocket serverSocket = new ServerSocket(listenPort,maxRequestNum)){
                while (true){
                    Socket accept = serverSocket.accept();
                    requestCount++;
                    Logger.i("server accept request:"+requestCount);
                    ThreadAgentUtils.getInstance().execute(new RtClient(rtContext,accept));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
