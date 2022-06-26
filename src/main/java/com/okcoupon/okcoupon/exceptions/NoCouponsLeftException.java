package com.okcoupon.okcoupon.exceptions;

public class NoCouponsLeftException extends Exception{
    public NoCouponsLeftException() {
        super("No coupons left");
    }

    public NoCouponsLeftException(String message) {
        super(message);
    }
}
