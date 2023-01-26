package com.bluty.springsecurity2023.exception;

public class BuninessException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public BuninessException(String message, Throwable cause){
        super(message, cause);
    }

    public BuninessException(String message){
        super(message);
    }
}