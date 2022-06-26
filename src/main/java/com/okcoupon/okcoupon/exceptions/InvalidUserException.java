package com.okcoupon.okcoupon.exceptions;

public class InvalidUserException extends Exception{
    public InvalidUserException() {
        super("Invalid user details! please try again or call your administrator");
    }

    public InvalidUserException(String message) {
        super(message);
    }
}
