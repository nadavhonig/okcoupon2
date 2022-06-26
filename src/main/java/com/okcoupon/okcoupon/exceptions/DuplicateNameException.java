package com.okcoupon.okcoupon.exceptions;

public class DuplicateNameException extends Exception{
    public DuplicateNameException() {
        super("This name already exists in the system, Please try another one");
    }

    public DuplicateNameException(String message) {
        super(message);
    }
}
