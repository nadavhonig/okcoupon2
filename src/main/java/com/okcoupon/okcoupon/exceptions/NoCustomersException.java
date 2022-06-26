package com.okcoupon.okcoupon.exceptions;

public class NoCustomersException extends Exception{
    public NoCustomersException() {
        super("Please add a new customer and try again");
    }

    public NoCustomersException(String message) {
        super(message);
    }
}
