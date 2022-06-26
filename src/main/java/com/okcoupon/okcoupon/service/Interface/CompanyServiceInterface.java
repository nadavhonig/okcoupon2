package com.okcoupon.okcoupon.service.Interface;

import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.exceptions.*;

import java.util.List;

public interface CompanyServiceInterface {
    /**
     * Method that indicated if the user that try to log in is exists in the dataBase
     * @param email unique part of the UserDetails performing the User-Name of the company
     * @param password unique part of the UserDetails performing the User-Password of the company
     * @return Integer indicated the Generated-key of the company try to log in, which is the ID
     */
    int login(String email, String password);
    /**
     * Method that called when the user want to add new coupon to the system
     * @param coupon instance of Coupon which holds all the details that coupon should have
     */
    void addCoupon(Coupon coupon);
    /**
     * Method that called when the user want to update an exists coupon
     * @param coupon instance of Coupon which holds all the details that coupon should have
     */
    void updateCoupon(Coupon coupon);
    /**
     * Method that called when the user want to delete one specific coupon
     * @param couponId Integer that indicates which coupon will be deleted, the ID is a generated-key of the coupon
     */
    void deleteCoupon(int couponId);
    /**
     * Method that called when the user expect to get all the coupons of the company
     * @param companyId Integer that indicates which company the user chose to get the details about, the ID is a generated-key of the company
     * @return List of all coupons of that company
     */
    List<Coupon> getCompanyCoupons(int companyId);
    /**
     * Method that called when the user expect to get one specific coupon
     * @param couponId Integer that indicates which coupon the user chose to get the details about, the ID is a generated-key of the coupon
     * @return instance of Coupon which holds all the details that coupon should have
     */
    public Coupon getOneCoupon(int couponId);
    /**
     * Method that called when the user expect to get all the coupons of the company filtered by Category
     * @param companyId Integer that indicates which company the user chose to get the details about, the ID is a generated-key of the company
     * @param category enum object that indicates which Category coupons will be filtered by
     * @return List of coupons filtered by the Category the user chose
     */
    List<Coupon> getCouponByCategory(int companyId, Category category);
    /**
     * Method that called when the user expect to get all the coupons of the customer filtered by Maximum Price
     * @param companyId Integer that indicates which company the user chose to get the details about, the ID is a generated-key of the company
     * @param price Double number that indicates the maximum price coupons will be filtered by
     * @return List of coupons filtered by the Maximum Price the user chose
     */
    List<Coupon> getCouponByMaxPrice(int companyId, double price);
    /**
     * Method that called when the user expect to get the details of specific company that exists in the system
     * @param companyId Integer that indicates which company the user chose to get the details about, the ID is a generated-key of the company
     * @return instance of Company which holds all the details that company should have
     */
    Company getCompanyDetails(int companyId);
}
