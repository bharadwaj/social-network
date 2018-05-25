package com.my.network.socialnetwork.model;

import java.util.ArrayList;
import java.util.List;

public class SendNotification {

    List<Tokens> tokens = new ArrayList<>();

    NotificationClass notification;

    int status;

    String message;

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

    public List<Tokens> getTokens() {
        return tokens;
    }

    public void setTokens(List<Tokens> tokens) {
        this.tokens = tokens;
    }

    public NotificationClass getNotification() {
        return notification;
    }

    public void setNotification(NotificationClass notification) {
        this.notification = notification;
    }
}
