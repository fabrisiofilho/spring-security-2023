package com.bluty.springsecurity2023.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Exception e) {
        super(message, e);
    }

}
