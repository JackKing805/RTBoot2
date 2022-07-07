package com.rtboot.boot.http.handler.impl.response.manager;

import com.rtboot.boot.http.handler.i.ResponseHandler;
import com.rtboot.boot.http.handler.impl.response.*;
import com.rtboot.boot.http.handler.model.ResponseMessage;
import com.rtboot.boot.rtboot.core.RtContext;
import com.rtboot.boot.rtboot.utils.Logger;

import java.net.URL;

public class ResponseHandlerManager {
    public static ResponseHandler handlerResponse(RtContext rtContext, ResponseMessage responseMessage){
        if (responseMessage.getContent()==null){
            return new NullContentResponseHandler();
        }
        if (responseMessage.getCode() >=400){
            return new ErrorResponseHandler();
        }

        Class<?> responseType = responseMessage.getContent().getClass();
        Logger.i("handlerResponse responseType:"+responseType);
        if (responseType==String.class){
            String content = (String) responseMessage.getContent();
            String stringProperties = rtContext.getStringProperties("http.view.suffix", ".html");
            if (content.endsWith(stringProperties)){
                return new ViewResponseHandler();
            }else {
                return new StringResponseHandler();
            }
        }else if(responseType == URL.class){
            return new URLResponseHandler();
        }
        return new ObjectResponseHandler();
    }
}
