package com.turkiyedenemeleri.model;

/**
 * Created by celal on 23/04/2017.
 */

public class MyHttpResponse<T>{
    int responseType;
    String responseMessage;
    T responseData;

    public int getResponseType() {
        return responseType;
    }

    public void setResponseType(int responseType) {
        this.responseType = responseType;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public T getData() {
        return responseData;
    }

    public void setData(T data) {
        this.responseData = data;
    }
}
