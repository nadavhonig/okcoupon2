package com.okcoupon.okcoupon.service.Interface;

import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.exceptions.*;

import java.util.List;

public interface CustomerServiceInterface {
    /**
     * Method that indicated if the user that try to log in is exists in the dataBase
     * @param email unique part of the UserDetails performing the User-Name of the customer
     * @param password unique part of the UserDetails performing the User-Password of the customer
     * @return Integer indicated the Generated-key of the company try to log in, which is the ID
     */
    int login(String email, String password);
    /**
     * Method that called when the user want to purchase a coupon
     * @param customer instance of Customer which holds the Generated-key which is the ID of the customer/user wants to execute the purchase
     * @param couponId Integer that indicates which coupon the user chose to purchase, the ID is a generated-key of the customer
     */
    void purchaseCoupon(Customer customer, int couponId);
    /**
     * Method that called when the user expect to get all the coupons he purchased
     * @param customerId Integer that indicates which customer the user chose to get the details about, the ID is a generated-key of the customer
     * @return List of all coupons the customer has purchased in the past
     */
    List<Coupon> getCustomerCoupons(int customerId);
    /**
     * Method that called when the user expect to get all the coupons of the customer filtered by Category
     * @param customerId Integer that indicates which customer the user chose to get the details about, the ID is a generated-key of the customer
     * @param category enum object that indicates which Category coupons will be filtered by
     * @return List of coupons filtered by the Category the user chose
     */
    List<Coupon> getCustomerCouponsByCategory(int customerId, Category category);
    /**
     * Method that called when the user expect to get all the coupons of the customer filtered by Maximum Price
     * @param customerId Integer that indicates which customer the user chose to get the details about, the ID is a generated-key of the customer
     * @param price Double number that indicates the maximum price coupons will be filtered by
     * @return List of coupons filtered by the Maximum Price the user chose
     */
    List<Coupon> getCustomerCouponsByPrice(int customerId, double price);
    /**
     * Method that called when the user expect to get the details of specific customer that exists in the system
     * @param customerId Integer that indicates which customer the user chose to get the details about, the ID is a generated-key of the customer
     * @return instance of Customer which holds all the details that customer should have
     */
    Customer getCustomerDetails(int customerId);
    /**
     * Method that called when the user expect to get all coupons in the system
     * @return List of coupons
     */
    List<Coupon> getAllCouponsInSystem();
}
