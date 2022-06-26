package com.okcoupon.okcoupon.exceptions;

public class NoCouponsCategoryException extends Exception{
    public NoCouponsCategoryException() {
        super("No coupons in of this category type. please try another category");
    }

    public NoCouponsCategoryException(String message) {
        super(message);
    }
}
