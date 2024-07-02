package com.qtsoftwareltd.invoicing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private String message;
    private T payload;

    public ApiResponse() {
        this.message = "Success";
    }

    public ApiResponse(T payload) {
        this.message = "Success";
        this.payload = payload;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(String message, T payload) {
        this.message = message;
        this.payload = payload;
    }
}
