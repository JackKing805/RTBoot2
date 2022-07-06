package com.rtboot.boot.http.extend;

import com.google.gson.Gson;
import com.rtboot.boot.http.bean.RequestHeader;
import com.rtboot.boot.http.bean.RequestUrl;
import com.rtboot.boot.http.enums.RequestProtocol;
import com.rtboot.boot.http.enums.RequestVersion;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.handler.model.ResponseWrapper;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.http.utils.StringParser;
import com.rtboot.boot.rtboot.utils.Logger;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Processor {
    private static final Gson gson = new Gson();

    public static RtRequest processRequest(Socket socket){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            boolean isProtocolLine = true;
            RequestProtocol requestProtocol = null;
            RequestUrl requestUrl = null;
            RequestVersion requestVersion = null;
            RequestHeader requestHeader = null;
            Map<String,Object> headers = new HashMap<>();
            while (true){
                String line = bufferedReader.readLine();
                if (isProtocolLine){
                    if (line==null){
                        return null;
                    }
                    String[] split = line.split(" ");
                    for (int i = 0; i < split.length; i++) {
                        String content = split[i];
                        if (i==0){
                            //method
                            requestProtocol = StringParser.parserRequestProtocol(content);
                        }else  if(i==1){
                            //link
                            requestUrl = new RequestUrl(content);
                        }else if(i==2){
                            //version
                            requestVersion = StringParser.parserRequestVersion(content);
                        }
                    }
                    isProtocolLine = false;
                }else if (line.contains(":")){
                    String[] strings = parseHeader(line);
                    if (strings!=null){
                        headers.put(strings[0],strings[1]);
                    }
                }else if (line.isEmpty()){
                    break;
                }
            }
            requestHeader = new RequestHeader(headers);
            if(requestProtocol != null && requestVersion != null && requestUrl != null){
                return new RtRequest(requestProtocol,requestVersion,requestUrl,requestHeader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String[] parseHeader(String content){
        if (content == null){
            return null;
        }
        if (content.isEmpty()){
            return null;
        }
        if(!content.contains(":")){
            return null;
        }
        String[] split = content.split(":");
        return StringParser.parseStringKV(split);
    }


    public static void processResponse(RtRequest rtRequest, RtResponse rtResponse, ResponseWrapper responseWrapper){
        if (rtResponse.isClose()){
            return;
        }

        Logger.i("request:"+ rtRequest.getRequestUrl().getPath()+",start response:"+ responseWrapper);

        if (responseWrapper==null){
            return;
        }

        try{
            OutputStream outputStream = rtResponse.getOutPutStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            String responseLine = rtRequest.getRequestVersion().getVersion()+" "+ responseWrapper.getResponseMessage().getCode() + " "+ responseWrapper.getResponseMessage().getMsg() + "\r\n";
            bufferedOutputStream.write(responseLine.getBytes());
            for (Map.Entry<String, Object> map : responseWrapper.getHeader().entrySet()) {
                String head = map.getKey() + map.getValue() + "\r\n";
                bufferedOutputStream.write(head.getBytes());
            }
            String endLine = "\r\n";
            bufferedOutputStream.write(endLine.getBytes());
            bufferedOutputStream.flush();
            if (responseWrapper.getResponseMessage().getContent()!=null && responseWrapper.getResponseWriteListener()!=null){
                responseWrapper.getResponseWriteListener().write(outputStream);
            }
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
