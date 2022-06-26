package com.okcoupon.okcoupon.exceptions;

public class CouponNotFoundException extends Exception{
    public CouponNotFoundException() {
        super("No coupon found");
    }

    public CouponNotFoundException(String message) {
        super(message);
    }
}
