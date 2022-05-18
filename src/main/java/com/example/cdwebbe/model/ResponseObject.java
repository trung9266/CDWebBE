package com.example.cdwebbe.model;

public class ResponseObject {
    private String status;
    private String message;
    private Object object;

    public ResponseObject() {
    }

    public ResponseObject(String status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
