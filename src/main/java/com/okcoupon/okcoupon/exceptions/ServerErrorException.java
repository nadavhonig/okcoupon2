package com.okcoupon.okcoupon.exceptions;

public class ServerErrorException extends Exception {
    public ServerErrorException() {
        super("Server error!!!");
    }

    public ServerErrorException(String message) {
        super(message);
    }
}
