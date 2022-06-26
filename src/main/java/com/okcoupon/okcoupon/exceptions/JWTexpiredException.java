package com.okcoupon.okcoupon.exceptions;

public class JWTexpiredException extends Exception {
    public JWTexpiredException() {
        super("You didn't do anything for a long time, please login again");
    }

    public JWTexpiredException(String message) {
        super(message);
    }
}
