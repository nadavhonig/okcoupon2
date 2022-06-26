package com.okcoupon.okcoupon.exceptions;

public class UncompletedFieldsException extends Exception{
    public UncompletedFieldsException() {
        super("You have not complete all the fields, please try again");
    }

    public UncompletedFieldsException(String message) {
        super(message);
    }
}
