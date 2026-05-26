package com.acropolis.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    @JsonProperty("is_success")
    private boolean success;

    @JsonProperty("message")
    private String message;

    public ErrorResponse(String message) {
        this.success = false;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
