package com.okcoupon.okcoupon.exceptions;

public class NoCompaniesException extends Exception{
    public NoCompaniesException() {
        super("Please add a new company and try again");
    }

    public NoCompaniesException(String message) {
        super(message);
    }
}
