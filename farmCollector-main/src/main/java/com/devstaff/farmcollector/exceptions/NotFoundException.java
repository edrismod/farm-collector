package com.devstaff.farmcollector.exceptions;

public class NotFoundException extends Exception{
    public NotFoundException(String msg) {
        super(msg);
    }
    public NotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
