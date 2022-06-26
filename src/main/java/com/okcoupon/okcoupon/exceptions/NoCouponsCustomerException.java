package com.okcoupon.okcoupon.exceptions;

public class NoCouponsCustomerException extends Exception{
    public NoCouponsCustomerException() {
        super("Please buy at least one coupon and try again");
    }

    public NoCouponsCustomerException(String message) {
        super(message);
    }
}
