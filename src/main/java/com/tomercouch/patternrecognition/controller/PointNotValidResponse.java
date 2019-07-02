package com.tomercouch.patternrecognition.controller;

public class PointNotValidResponse {
    private static String responseName = "Point could not be added";
    private String message;

    public PointNotValidResponse(String message) {
        this.message = message;
    }

    public static String getResponseName() {
        return responseName;
    }

    public static void setResponseName(String responseName) {
        PointNotValidResponse.responseName = responseName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
