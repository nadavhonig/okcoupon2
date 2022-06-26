package com.okcoupon.okcoupon.exceptions;

public class NoCouponsCompanyException extends Exception{
    public NoCouponsCompanyException() {
        super("Please add a new coupon and try again");
    }

    public NoCouponsCompanyException(String message) {
        super(message);
    }
}
