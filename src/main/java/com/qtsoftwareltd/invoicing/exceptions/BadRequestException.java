package com.qtsoftwareltd.invoicing.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException {
    private String message;

    public BadRequestException() {
        super("Invalid request");
    }

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }
}
