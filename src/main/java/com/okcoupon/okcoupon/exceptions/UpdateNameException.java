package com.okcoupon.okcoupon.exceptions;

public class UpdateNameException extends Exception{
    public UpdateNameException() {
        super("You can't update the name, it's illegal");
    }

    public UpdateNameException(String message) {
        super(message);
    }
}
