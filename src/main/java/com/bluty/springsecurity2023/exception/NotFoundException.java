package com.bluty.springsecurity2023.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public NotFoundException(String message) {
        super(message);
    }

}
