package com.okcoupon.okcoupon.service.Interface;

import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.exceptions.DuplicateItemException;
import com.okcoupon.okcoupon.exceptions.NoCouponsCategoryException;
import com.okcoupon.okcoupon.exceptions.NoCouponsPriceException;

import java.util.List;

public interface GuestServiceInterface {
    /**
     * Method that called when the user expect to get all coupons in the system
     * @return List of coupons
     */
    List<Coupon> getAllCouponsInSystem();
    /**
     * Method that invoked when the user expect to get all the coupons filtered by Maximum Price
     * @param price Double number that indicates the maximum price coupons will be filtered by
     * @return List of coupons filtered by the Maximum Price the user chose
     */
    List<Coupon> getCouponsByMaxPrice(double price);
    /**
     * Method that invoked when the user expect to get all the coupons filtered by Category
     * @param category enum object that indicates which Category coupons will be filtered by
     * @return List of coupons filtered by the Category the user chose
     */
    List<Coupon> getCouponsByCategory(Category category);
    /**
     * Method that invoked when the user want to register to the system
     * @param customer instance of Customer which holds all the details that customer should have
     */
    void register(Customer customer);
}
