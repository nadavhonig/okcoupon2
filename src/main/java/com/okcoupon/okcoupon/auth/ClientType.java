package com.okcoupon.okcoupon.auth;



public enum ClientType {
    ADMIN("ADMIN"),
    COMPANY("COMPANY"),
    CUSTOMER("CUSTOMER");

    private String type;

    /**
     * constructor
     * @param type is a string which indicates the type of the enum
     */
    ClientType(String type) {
        this.type = type;
    }

    /**
     * get method
     * @return the type of the enum
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
