package com.okcoupon.okcoupon.service;

import com.okcoupon.okcoupon.auth.UserDetails;
import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.exceptions.DuplicateItemException;
import com.okcoupon.okcoupon.exceptions.NoCouponsCategoryException;
import com.okcoupon.okcoupon.exceptions.NoCouponsCompanyException;
import com.okcoupon.okcoupon.exceptions.NoCouponsPriceException;
import com.okcoupon.okcoupon.repositories.CouponRepo;
import com.okcoupon.okcoupon.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private CustomerRepo customerRepo;

    /**
     * Method that called when the user expect to get all coupons in the system
     * @return List of coupons
     */
    public List<Coupon> getAllCouponsInSystem(){
        return couponRepo.findAll();
    }
    /**
     * Method that invoked when the user expect to get all the coupons filtered by Maximum Price
     * @param price Double number that indicates the maximum price coupons will be filtered by
     * @return List of coupons filtered by the Maximum Price the user chose
     * @throws NoCouponsPriceException thrown when there are no coupons up to this chosen Price
     */
    public List<Coupon> getCouponsByMaxPrice(double price) throws NoCouponsPriceException {
        List<Coupon> coupons = couponRepo.findByPriceLessThan(price);
        if (coupons.isEmpty()) {
            throw new NoCouponsPriceException();
        }
        return coupons;
    }
    /**
     * Method that invoked when the user expect to get all the coupons filtered by Category
     * @param category enum object that indicates which Category coupons will be filtered by
     * @return List of coupons filtered by the Category the user chose
     * @throws NoCouponsCategoryException thrown when there are no coupons in this chosen Category
     */
    public List<Coupon> getCouponsByCategory(Category category) throws NoCouponsCategoryException {
        List<Coupon> coupons = couponRepo.findByCategory(category);
        if (coupons.isEmpty()){
            throw new NoCouponsCategoryException();
        }
        return coupons;
    }
    /**
     * Method that invoked when the user want to register to the system
     * It runs one condition and checks if there is already a customer with the same Username which is the email in the system
     * if all the details are valid it adds a record to the database in table Customers
     * @param customer instance of Customer which holds all the details that customer should have
     * @throws DuplicateItemException thrown when user try to add customer that already exists in the system
     */
    public void register(Customer customer) throws DuplicateItemException {
        if (!customerRepo.existsByEmail(customer.getEmail())) {
            customerRepo.saveAndFlush(customer);
        } else throw new DuplicateItemException();
    }
}
