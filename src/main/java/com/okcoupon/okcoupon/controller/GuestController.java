package com.okcoupon.okcoupon.controller;

import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.exceptions.*;
import com.okcoupon.okcoupon.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("guest")
public class GuestController {

    @Autowired
    GuestService guestService;
    /**
     * Get-method (READ) that called when the user expect to get all coupons in the system
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The List of the coupons passed throw the body part
     */
    @GetMapping("/allCouponsInSystem")
    public ResponseEntity<?> getAllCouponsInSystem() {
        return new ResponseEntity<>(guestService.getAllCouponsInSystem(), HttpStatus.ACCEPTED);
    }
    /**
     * Get-method (READ) that called when the user expect to get all the coupons filtered by Maximum Price
     * @param price Double number that indicates the maximum price coupons will be filtered by
     * @return ResponseEntity with HttpStatus which is 200-OK
     * The list of the coupons passed throw the body part
     * @throws NoCouponsPriceException thrown when there are no coupons up to this chosen Price
     */
    @GetMapping("/couponsByPrice{price}")
    public ResponseEntity<?> getCouponsByMaxPrice( @PathVariable double price) throws NoCouponsPriceException {
        return new ResponseEntity<>(guestService.getCouponsByMaxPrice(price), HttpStatus.ACCEPTED);
    }
    /**
     * Get-method (READ) that called when the user expect to get all the coupons filtered by Category
     * @param category enum object that indicates which Category coupons will be filtered by
     * @return ResponseEntity with HttpStatus which is 200-OK
     * The list of the coupons passed throw the body part
     * @throws NoCouponsCategoryException thrown when there are no coupons in this chosen Category
     */
    @GetMapping("/couponsByCategory{category}")
    public ResponseEntity<?> getCouponsByCategory (@PathVariable Category category) throws NoCouponsCategoryException {
        return new ResponseEntity<>(guestService.getCouponsByCategory(category),HttpStatus.ACCEPTED);
    }
    /**
     * Post-method (CREATE) that called when the user want to register as a new customer to the system
     * @param customer instance of Customer which holds all the details that customer should have
     *        return with HttpStatus which is 201-CREATED
     * @throws DuplicateItemException thrown when user try to add customer that already exists in the system
     * @throws UncompletedFieldsException thrown when passed customer-object that missing some part/field
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register (@RequestBody Customer customer) throws DuplicateItemException, UncompletedFieldsException {
        if (customer.unCompleteFields()) {
            throw new UncompletedFieldsException();
        }
        guestService.register(customer);
    }
}
