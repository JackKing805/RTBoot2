package com.rtboot.boot.http.handler.impl.response;

import com.rtboot.boot.http.handler.i.ResponseHandler;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.http.handler.model.ResponseWrapper;
import com.rtboot.boot.http.model.RtRequest;
import com.rtboot.boot.http.model.RtResponse;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class ViewResponseHandler extends ResponseHandler {
    @Override
    public ResponseWrapper handlerResponse(RtContext rtContext, RtRequest rtRequest, RtResponse rtResponse, ResponseMessage responseMessage) {
        Logger.i("handlerResponse ViewResponseHandler");
        String url = (String) responseMessage.getContent();
        String prefix = rtContext.getStringProperties("http.resources.prefix", "static");
        String path = prefix+"/"+url;
        URL resources = rtContext.getResources(path);
        File file = new File(resources.getFile());
        ResponseWrapper responseWrapper = new ResponseWrapper(rtRequest, responseMessage, outputStream -> {
            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) !=-1){
                outputStream.write(bytes,0,len);
            }
            Logger.i("length:"+len);
        });
        responseWrapper.setContentType(getContentType(rtRequest));
        responseWrapper.setContentLength(file.length());
        return responseWrapper;
    }
}
