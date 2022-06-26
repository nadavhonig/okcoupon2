package com.okcoupon.okcoupon.exceptions;

public class DuplicateItemException extends Exception {
    public DuplicateItemException() {
        super("Already exists in the system");
    }

    public DuplicateItemException(String message) {
        super(message);
    }
}
