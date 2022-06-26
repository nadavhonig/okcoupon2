package com.okcoupon.okcoupon.service;

import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.exceptions.*;
import com.okcoupon.okcoupon.repositories.CompanyRepo;
import com.okcoupon.okcoupon.repositories.CouponRepo;
import com.okcoupon.okcoupon.repositories.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private PurchaseRepo purchaseRepo;

    /**
     * Method that indicated if the user that try to log in is exists in the dataBase
     * @param email unique part of the UserDetails performing the User-Name of the company
     * @param password unique part of the UserDetails performing the User-Password of the company
     * @return Integer indicated the Generated-key of the company try to log in, which is the ID
     * @throws InvalidUserException thrown when passed invalid user-details
     */
    public int login(String email, String password) throws InvalidUserException {
        if (companyRepo.existsByEmailAndPassword(email, password)) {
            return companyRepo.findCompanyByEmailAndPassword(email, password).getId();
        }
        throw new InvalidUserException();
    }

    /**
     * Method that called when the user want to add new coupon to the system
     * It runs some conditions that check if:
     *      *               There is already a coupon with the same name for this company in the system
     *      *               The coupon Expiration Date has passed
     * @param coupon instance of Coupon which holds all the details that coupon should have
     *
     * @throws DuplicateItemException thrown when user try to add coupon that already exists in the system
     * @throws ExpiredCouponException thrown when the coupon Expiration Date has passed
     * @throws  CompanyNameMismatchException thrown when sends an invalid-company-name
     */
    public void addCoupon(Coupon coupon) throws DuplicateItemException, ExpiredCouponException, CompanyNameMismatchException {
        if (!couponRepo.existsByTitleAndCompanyId(coupon.getTitle(), coupon.getCompany().getId())) {
            if (coupon.getCompany().getName().equals(coupon.getCompanyName())) {
                if (coupon.getEndDate().after(Date.from(Instant.now()))) {
                    couponRepo.saveAndFlush(coupon);
                } else throw new ExpiredCouponException();
            } else throw new CompanyNameMismatchException();
        } else throw new DuplicateItemException();
    }

    /**
     * Method that called when the user want to update an exists coupon
     * @param coupon instance of Coupon which holds all the details that coupon should have
     * @throws CouponNotFoundException thrown when user sends an invalid-coupon-id that doesn't exist in the system
     * @throws  CompanyNameMismatchException thrown when sends an invalid-company-name
     */
    public void updateCoupon(Coupon coupon) throws CouponNotFoundException, CompanyNameMismatchException {
        if (couponRepo.existsById(coupon.getId())) {
            if (couponRepo.getById(coupon.getId()).getCompanyName().equals(coupon.getCompanyName())) {
                couponRepo.saveAndFlush(coupon);
            } else throw new CompanyNameMismatchException();
        } else throw new CouponNotFoundException();
    }

    /**
     * Method that called when the user want to delete one specific coupon
     * @param couponId Integer that indicates which coupon will be deleted, the ID is a generated-key of the coupon
     * @throws NotFoundException thrown when user sends an invalid-coupon-id that doesn't exist in the system
     */
    public void deleteCoupon(int couponId) throws NotFoundException {
        if (couponRepo.existsById(couponId)) {
            purchaseRepo.deleteByCouponId(couponId);
            couponRepo.deleteById(couponId);
        } else throw new NotFoundException();
    }

    /**
     * Method that called when the user expect to get all the coupons of the company
     * @param companyId Integer that indicates which company the user chose to get the details about, the ID is a generated-key of the company
     * @return List of all coupons of that company
     * @throws NoCouponsCompanyException thrown when there are no coupons for this company in the system
     * @throws NotFoundException thrown when user sends an invalid-company-id that doesn't exist in the system
     */
    public List<Coupon> getCompanyCoupons(int companyId) throws NoCouponsCompanyException, NotFoundException {
        if (!companyRepo.existsById(companyId)) {
            throw new NotFoundException();
        }
        if(couponRepo.findByCompanyId(companyId).isEmpty()){
            throw new NoCouponsCompanyException();
        }
        return couponRepo.findByCompanyId(companyId);
    }

    /**
     * Method that called when the user expect to get one specific coupon
     * @param couponId Integer that indicates which coupon the user chose to get the details about, the ID is a generated-key of the coupon
     * @return instance of Coupon which holds all the details that coupon should have
     * @throws NotFoundException thrown when user sends an invalid-coupon-id that doesn't exist in the system
     */
    public Coupon getOneCoupon(int couponId) throws NotFoundException {
        if(!couponRepo.existsById(couponId)){
            throw new NotFoundException();
        }
        return couponRepo.getById(couponId);
    }

    /**
     * Method that called when the user expect to get all the coupons of the company filtered by Category
     * @param companyId Integer that indicates which company the user chose to get the details about, the ID is a generated-key of the company
     * @param category enum object that indicates which Category coupons will be filtered by
     * @return List of coupons filtered by the Category the user chose
     * @throws NoCouponsCategoryException thrown when there are no coupons in this chosen Category
     * @throws NoCouponsCompanyException thrown when there are no coupons for this company in the system
     */
    public List<Coupon> getCouponByCategory(int companyId, Category category) throws NoCouponsCategoryException, NoCouponsCompanyException {
        if(couponRepo.findByCompanyId(companyId).isEmpty()){
            throw new NoCouponsCompanyException();
        }
        List<Coupon> coupons = couponRepo.findByCompanyIdAndCategory(companyId, category);
        if (coupons.isEmpty()) {
            throw new NoCouponsCategoryException();
        }
        return coupons;
    }

    /**
     * Method that called when the user expect to get all the coupons of the customer filtered by Maximum Price
     * @param companyId Integer that indicates which company the user chose to get the details about, the ID is a generated-key of the company
     * @param price Double number that indicates the maximum price coupons will be filtered by
     * @return List of coupons filtered by the Maximum Price the user chose
     * @throws NoCouponsPriceException thrown when there are no coupons up to this chosen Price
     * @throws NoCouponsCompanyException thrown when there are no coupons for this company in the system
     */
    public List<Coupon> getCouponByMaxPrice(int companyId, double price) throws NoCouponsPriceException, NoCouponsCompanyException {
        if(couponRepo.findByCompanyId(companyId).isEmpty()){
            throw new NoCouponsCompanyException();
        }
        List<Coupon> coupons = couponRepo.findByCompanyIdAndPriceLessThan(companyId, price);
        if (coupons.isEmpty()) {
            throw new NoCouponsPriceException();
        }
        return coupons;
    }
    /**
     * Method that called when the user expect to get the details of specific company that exists in the system
     * @param companyId Integer that indicates which company the user chose to get the details about, the ID is a generated-key of the company
     * @return instance of Company which holds all the details that company should have
     * @throws NotFoundException thrown when user sends an invalid-id that doesn't exist in the system
     */
    public Company getCompanyDetails(int companyId) throws NotFoundException {
        if (companyRepo.existsById(companyId)) {
            return companyRepo.getById(companyId);
        }
        throw new NotFoundException();
    }
}
