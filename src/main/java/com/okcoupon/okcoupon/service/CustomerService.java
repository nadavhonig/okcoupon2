package com.okcoupon.okcoupon.service;

import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.beans.Purchase;
import com.okcoupon.okcoupon.exceptions.*;
import com.okcoupon.okcoupon.repositories.CouponRepo;
import com.okcoupon.okcoupon.repositories.CustomerRepo;
import com.okcoupon.okcoupon.repositories.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PurchaseRepo purchaseRepo;

    /**
     * Method that indicated if the user that try to log in is exists in the dataBase
     * @param email unique part of the UserDetails performing the User-Name of the customer
     * @param password unique part of the UserDetails performing the User-Password of the customer
     * @return Integer indicated the Generated-key of the company try to log in, which is the ID
     * @throws InvalidUserException thrown when passed invalid user-details
     */
    public int login(String email, String password) throws InvalidUserException {
        if (customerRepo.existsByEmailAndPassword(email, password)) {
            return customerRepo.findCustomerByEmailAndPassword(email, password).getId();
        }
        throw new InvalidUserException();
    }

    /**
     * Method that called when the user want to purchase a coupon
     * It runs some conditions that check if:
     * The coupon exists in the system,
     * The coupon amount is above zero
     * The user has never purchased this coupon in the past
     * The coupon has not expired
     * @param customer instance of Customer which holds the Generated-key which is the ID of the customer/user wants to execute the purchase
     * @param couponId Integer that indicates which coupon the user chose to purchase, the ID is a generated-key of the customer
     * @throws NoCouponsLeftException thrown when there are no coupons left, amount is 0
     * @throws SamePurchaseException thrown when the user try to purchase that he already bought in the past
     * @throws ExpiredCouponException thrown when the coupon Expiration Date has passed
     * @throws NotFoundException thrown when user sends an invalid-coupon-id that doesn't exist in the system
     */
    public void purchaseCoupon(Customer customer, int couponId) throws NoCouponsLeftException, SamePurchaseException, ExpiredCouponException, NotFoundException {
        if (!couponRepo.existsById(couponId)){
            throw new   NotFoundException();
        }
            Coupon coupon = couponRepo.getById(couponId);
        if (coupon.getAmount() > 0) {
            if (customer.getPurchases().stream().noneMatch(purchase -> purchase.getCoupon().getId() == couponId)) {
                if (coupon.getEndDate().after(Date.from(Instant.now()))) {
                    purchaseRepo.saveAndFlush(new Purchase(customer, coupon));
                    coupon.setAmount(coupon.getAmount() - 1);
                    couponRepo.saveAndFlush(coupon);
                } else throw new ExpiredCouponException();
            } else throw new SamePurchaseException();
        } else throw new NoCouponsLeftException();
    }

    /**
     * Method that called when the user expect to get all the coupons he purchased
     * First it finds a List of all customer's Purchases and Then mapped it to List of Coupons
     * @param customerId Integer that indicates which customer the user chose to get the details about, the ID is a generated-key of the customer
     * @return List of all coupons the customer has purchased in the past
     * @throws NoCouponPurchasesException thrown when there are no coupons for this company in the system
     */
    public List<Coupon> getCustomerCoupons(int customerId) throws NoCouponPurchasesException {
        List<Purchase> purchases = purchaseRepo.findByCustomerId(customerId);
        if (purchases.isEmpty()){
            throw new NoCouponPurchasesException();
        }
        Function<Purchase, Coupon> purchaseToCoupon = purchase ->
                new Coupon(
                        purchase.getCoupon().getId(),
                        purchase.getCoupon().getCompany(),
                        purchase.getCoupon().getCompanyName(),
                        purchase.getCoupon().getCategory(),
                        purchase.getCoupon().getTitle(),
                        purchase.getCoupon().getDescription(),
                        purchase.getCoupon().getStartDate(),
                        purchase.getCoupon().getEndDate(),
                        purchase.getCoupon().getAmount(),
                        purchase.getCoupon().getPrice(),
                        purchase.getCoupon().getImage());
        return purchases.stream()
                .map(purchaseToCoupon)
                .collect(Collectors.toList());
    }

    /**
     * Method that called when the user expect to get all the coupons of the customer filtered by Category
     * @param customerId Integer that indicates which customer the user chose to get the details about, the ID is a generated-key of the customer
     * @param category enum object that indicates which Category coupons will be filtered by
     * @return List of coupons filtered by the Category the user chose
     * @throws NoCouponsCategoryException thrown when there are no coupons in this chosen Category
     * @throws NoCouponPurchasesException thrown when there are no coupons for this company in the system
     */
    public List<Coupon> getCustomerCouponsByCategory(int customerId, Category category) throws NoCouponsCategoryException, NoCouponPurchasesException {
        List<Coupon> coupons = getCustomerCoupons(customerId).stream()
                .filter(coupon -> coupon.getCategory().equals(category))
                .collect(Collectors.toList());
        if (coupons.isEmpty()) {
            throw new NoCouponsCategoryException();
        }
        return coupons;
    }

    /**
     * Method that called when the user expect to get all the coupons of the customer filtered by Maximum Price
     * @param customerId Integer that indicates which customer the user chose to get the details about, the ID is a generated-key of the customer
     * @param price Double number that indicates the maximum price coupons will be filtered by
     * @return List of coupons filtered by the Maximum Price the user chose
     * @throws NoCouponsPriceException thrown when there are no coupons up to this chosen Price
     * @throws NoCouponPurchasesException thrown when there are no coupons for this company in the system
     */
    public List<Coupon> getCustomerCouponsByPrice(int customerId, double price) throws NoCouponsPriceException, NoCouponPurchasesException {
        List<Coupon> coupons = getCustomerCoupons(customerId).stream()
                .filter(coupon -> coupon.getPrice() < price)
                .collect(Collectors.toList());
        if (coupons.isEmpty()) {
            throw new NoCouponsPriceException();
        }
        return coupons;
    }

    /**
     * Method that called when the user expect to get the details of specific customer that exists in the system
     * @param customerId Integer that indicates which customer the user chose to get the details about, the ID is a generated-key of the customer
     * @return instance of Customer which holds all the details that customer should have
     * @throws NotFoundException thrown when user sends an invalid-id that doesn't exist in the system
     */
    public Customer getCustomerDetails(int customerId) throws NotFoundException {
        if (customerRepo.existsById(customerId)) {
            return customerRepo.getById(customerId);
        }
        throw new NotFoundException();
    }
    /**
     * Method that called when the user expect to get all coupons in the system
     * @return List of coupons
     */
    public List<Coupon> getAllCouponsInSystem(){
        return couponRepo.findAll();
    }
}
