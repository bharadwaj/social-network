package com.my.network.socialnetwork.model.response;

public class MyNetworkSubscriptionResponse {

    public MyNetworkSubscriptionResponse() {
    }

    public MyNetworkSubscriptionResponse(String statusCode, Integer statusCodeValue) {
        this.statusCode = statusCode;
        this.statusCodeValue = statusCodeValue;
    }

    private String statusCode;


    private Integer statusCodeValue;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getStatusCodeValue() {
        return statusCodeValue;
    }

    public void setStatusCodeValue(Integer statusCodeValue) {
        this.statusCodeValue = statusCodeValue;
    }
}

