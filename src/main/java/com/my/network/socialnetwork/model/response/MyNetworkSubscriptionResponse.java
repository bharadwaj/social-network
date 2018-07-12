package com.my.network.socialnetwork.model.response;

import com.my.network.socialnetwork.model.SubscribedUser;

public class MyNetworkSubscriptionResponse {

    public MyNetworkSubscriptionResponse() {
    }

    public MyNetworkSubscriptionResponse(String statusCode, Integer statusCodeValue, SubscribedUser subscribedUser) {
        this.statusCode = statusCode;
        this.statusCodeValue = statusCodeValue;
        this.subscribedUser = subscribedUser;
    }

    private String statusCode;


    private Integer statusCodeValue;

    private SubscribedUser subscribedUser;

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

    public SubscribedUser getSubscribedUser() {
        return subscribedUser;
    }

    public void setSubscribedUser(SubscribedUser subscribedUser) {
        this.subscribedUser = subscribedUser;
    }
}

