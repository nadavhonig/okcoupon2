package com.okcoupon.okcoupon.jasonViews;

/**
 * class that support @JsonView and use for organized which fields of the json will be seen and who will not be seen
 */
public class Views {

    public static class Public {
    }

    public static class Internal extends Public {
    }
}
