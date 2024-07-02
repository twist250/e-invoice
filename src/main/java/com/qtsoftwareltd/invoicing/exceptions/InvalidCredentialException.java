package com.qtsoftwareltd.invoicing.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidCredentialException extends RuntimeException {
    private String message;

    public InvalidCredentialException() {
        super("Invalid credentials");
        this.message = "Invalid credentials";
    }

    public InvalidCredentialException(String message) {
        super(message);
        this.message = message;
    }
}
