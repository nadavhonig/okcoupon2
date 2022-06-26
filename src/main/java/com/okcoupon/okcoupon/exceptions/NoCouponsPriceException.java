package com.okcoupon.okcoupon.exceptions;

public class NoCouponsPriceException extends Exception{
    public NoCouponsPriceException() {
        super("No coupons below this price type. please try bigger price");
    }

    public NoCouponsPriceException(String message) {
        super(message);
    }
}
