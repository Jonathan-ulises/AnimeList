package com.macrobios.mangalist.api;

public class StatusDescription {
    private RequestStatus status;
    private String message;

    public StatusDescription(RequestStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
