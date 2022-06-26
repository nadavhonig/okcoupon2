package com.okcoupon.okcoupon.exceptions;

public class UnknownRoleException extends Exception{
    public UnknownRoleException() {
        super("Unknown Role. please try again");
    }

    public UnknownRoleException(String message) {
        super(message);
    }
}
