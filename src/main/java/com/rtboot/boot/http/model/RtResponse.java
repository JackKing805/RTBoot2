package com.rtboot.boot.http.model;

import com.rtboot.boot.http.enums.RequestProtocol;
import com.rtboot.boot.http.enums.RequestVersion;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class RtResponse{
    private final Socket socket;

    public RtResponse(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public OutputStream getOutPutStream() throws IOException {
        return socket.getOutputStream();
    }

    public BufferedOutputStream getBufferedOutPutStream() throws IOException {
        return new BufferedOutputStream(socket.getOutputStream());
    }

    public PrintWriter getPrintWriter() throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }

    private boolean isClose =false;
    public void close() throws IOException {
        if(!isClose){
            isClose = true;
            socket.close();
        }
    }
}
