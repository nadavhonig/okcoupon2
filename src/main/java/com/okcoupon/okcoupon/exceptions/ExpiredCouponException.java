package com.okcoupon.okcoupon.exceptions;

public class ExpiredCouponException extends Exception {
    public ExpiredCouponException() {
        super("The date of coupon has passed");
    }

    public ExpiredCouponException(String message) {
        super(message);
    }
}
