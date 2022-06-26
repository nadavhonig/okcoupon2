package com.okcoupon.okcoupon.exceptions;

public class NotFoundException extends Exception{
    public NotFoundException() {
        super("Item can not be found, please try again with different details");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
