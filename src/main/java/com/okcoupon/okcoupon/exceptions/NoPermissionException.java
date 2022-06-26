package com.okcoupon.okcoupon.exceptions;

public class NoPermissionException extends Exception{
    public NoPermissionException() {
        super("You have no permission to do it, please call your administrator");
    }

    public NoPermissionException(String message) {
        super(message);
    }
}
