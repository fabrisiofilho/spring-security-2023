package com.bluty.springsecurity2023.exception;

public class TokenException extends RuntimeException {

    public TokenException(String message) {
        super(message);
    }

    public TokenException(Exception e) {
        super(e);
    }

    public TokenException(String message, Exception e) {
        super(message, e);
    }
}
