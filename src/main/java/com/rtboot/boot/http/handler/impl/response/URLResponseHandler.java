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

public class URLResponseHandler extends ResponseHandler {
    @Override
    public ResponseWrapper handlerResponse(RtContext rtContext, RtRequest rtRequest, RtResponse rtResponse, ResponseMessage responseMessage){
        Logger.i("handlerResponse URLResponseHandler");
        URL url = (URL) responseMessage.getContent();
        File file = new File(url.getFile());

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
        });
        responseWrapper.setContentType(getContentType(rtRequest));
        responseWrapper.setContentLength(file.length());
        return responseWrapper;
    }
}
