package com.my.network.socialnetwork.model.response;

import org.springframework.http.HttpStatus;

public class SuccessResponse {

    private int status;
    private String success;
    private String message;
    private String timestamp;
    private String path;
    private Object data;

    public SuccessResponse(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.success = httpStatus.getReasonPhrase();
        this.message = message;
        this.timestamp = new java.util.Date().toString();
        this.path = "";
        this.data = null;
    }

    public SuccessResponse(HttpStatus httpStatus, String message, Object data, String path) {
        this.status = httpStatus.value();
        this.success = httpStatus.getReasonPhrase();
        this.message = message;
        this.timestamp = new java.util.Date().toString();
        this.path = path;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
