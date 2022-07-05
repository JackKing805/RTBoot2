package com.rtboot.boot.http.handler.model;

import org.jetbrains.annotations.Nullable;

public class RequestResult {
    private final int type;//1:next -1:failure 0：success
    @Nullable
    private final ResponseMessage responseMessage;

    public RequestResult(int type,@Nullable ResponseMessage responseMessage) {
        this.type = type;
        this.responseMessage = responseMessage;
    }

    public int getType() {
        return type;
    }

    @Nullable
    public ResponseMessage getResponseMessage() {
        if(type==-1 || type == 0){
            if (responseMessage==null){
                throw new NullPointerException("request must have response");
            }
        }
        return responseMessage;
    }

    public static RequestResult next(){
        return new RequestResult(1,null);
    }

    public static RequestResult failure(ResponseMessage responseMessage){
        return new RequestResult(-1,responseMessage);
    }

    public static RequestResult success(ResponseMessage responseMessage){
        return new RequestResult(0,responseMessage);
    }

    public static boolean isNext(RequestResult requestResult){
        return requestResult.type == 1;
    }

    public static boolean isFailure(RequestResult requestResult){
        return requestResult.type == -1;
    }

    public static boolean isSuccess(RequestResult requestResult){
        return requestResult.type == 0;
    }
}
