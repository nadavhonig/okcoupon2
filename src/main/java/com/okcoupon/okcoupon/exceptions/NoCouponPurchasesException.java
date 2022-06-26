package com.okcoupon.okcoupon.exceptions;

public class NoCouponPurchasesException extends Exception{
    public NoCouponPurchasesException() {
        super("Customer has no purchase, Please buy any coupon and try again");
    }

    public NoCouponPurchasesException(String message) {
        super(message);
    }
}
