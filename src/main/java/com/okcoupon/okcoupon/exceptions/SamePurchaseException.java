package com.okcoupon.okcoupon.exceptions;

public class SamePurchaseException extends Exception {
    public SamePurchaseException() {
        super("you already bought this coupon before...");
    }

    public SamePurchaseException(String message) {
        super(message);
    }
}
