package com.okcoupon.okcoupon.exceptions;

public class CompanyNameMismatchException extends Exception{
        public CompanyNameMismatchException() {
                super("Please check the spelling of company name in form");
        }

        public CompanyNameMismatchException(String message) {
                super(message);
        }
}
