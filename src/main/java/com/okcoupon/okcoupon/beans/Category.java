package com.okcoupon.okcoupon.beans;

public enum Category {
    FASHION("FASHION"),
    ELECTRICITY("ELECTRICITY"),
    RESTAURANT("RESTAURANT"),
    VACATION("VACATION"),
    ENTERTAINMENT("ENTERTAINMENT"),
    DOGFOOD("DOGFOOD");

    private final String categoryType;

    Category(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryType() {
        return this.categoryType;
    }
}
