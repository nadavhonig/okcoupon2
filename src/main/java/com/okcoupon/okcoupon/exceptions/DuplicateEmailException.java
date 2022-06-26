package com.okcoupon.okcoupon.exceptions;

public class DuplicateEmailException extends Exception{
    public DuplicateEmailException() {
        super("This email already exists in system, Please try another one");
    }

    public DuplicateEmailException(String message) {
        super(message);
    }
}
