package com.okcoupon.okcoupon.exceptions;

public class InvalidEmailException extends Exception{
    public InvalidEmailException() {
        super("Invalid email");
    }

    public InvalidEmailException(String message) {
        super(message);
    }
}
