package com.sykora.chatapp.config.exception;

public class KeyException extends RuntimeException {
    public KeyException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
}
