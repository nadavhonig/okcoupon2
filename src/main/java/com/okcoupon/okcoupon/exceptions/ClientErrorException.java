package com.okcoupon.okcoupon.exceptions;

public class ClientErrorException extends Exception {

    public ClientErrorException() {
        super("Client error!!!");
    }

    public ClientErrorException(String message) {
        super(message);
    }
}
