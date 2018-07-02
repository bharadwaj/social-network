package com.my.network.socialnetwork.notification;

import java.util.ArrayList;
import java.util.List;

public class SendNotification {

    private List<UserGcmToken> tokens = new ArrayList<>();

    private MyDukanNotification notification;

    private int status;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserGcmToken> getTokens() {
        return tokens;
    }

    public void setTokens(List<UserGcmToken> tokens) {
        this.tokens = tokens;
    }

    public MyDukanNotification getNotification() {
        return notification;
    }

    public void setNotification(MyDukanNotification notification) {
        this.notification = notification;
    }
}
