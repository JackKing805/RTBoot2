package com.rtboot.boot.http.handler.i.model;

import com.rtboot.boot.http.model.Response;
import org.jetbrains.annotations.Nullable;

public class RequestHandlerResponse {
    private final int type;
    @Nullable
    private final Response response;

    public RequestHandlerResponse(int type,@Nullable Response response) {
        this.type = type;
        this.response = response;
    }

    public int getType() {
        return type;
    }

    @Nullable
    public Response getResponse() {
        return response;
    }


    public static RequestHandlerResponse continueNext(){
        return new RequestHandlerResponse(0,null);
    }

    public static RequestHandlerResponse success(@Nullable Response response){
        return new RequestHandlerResponse(1,response);
    }

    public static RequestHandlerResponse failure(@Nullable Response response){
        return new RequestHandlerResponse(-1,response);
    }

    public static boolean isContinue(RequestHandlerResponse response){
        return response.getType()==0;
    }

    public static boolean isSuccess(RequestHandlerResponse response){
        return response.getType()==1;
    }

    public static boolean isFailure(RequestHandlerResponse response){
        return response.getType()==-1;
    }
}
