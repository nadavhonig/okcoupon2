package com.okcoupon.okcoupon.exceptions;

public class UpdateIDException extends Exception{
    public UpdateIDException() {
        super("You can't update the ID, it's illegal");
    }

    public UpdateIDException(String message) {
        super(message);
    }
}
