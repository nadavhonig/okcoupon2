package com.okcoupon.okcoupon.exceptions;

public class UnknownCategoryException extends Exception{
    public UnknownCategoryException() {
        super("Unknown Category. please try again");
    }

    public UnknownCategoryException(String message) {
        super(message);
    }
}
