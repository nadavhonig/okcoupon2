package com.okcoupon.okcoupon.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.okcoupon.okcoupon.auth.ClientType;
import com.okcoupon.okcoupon.auth.JWT;
import com.okcoupon.okcoupon.auth.UserDetails;
import com.okcoupon.okcoupon.beans.Category;

import com.okcoupon.okcoupon.exceptions.*;
import com.okcoupon.okcoupon.jasonViews.Views;
import com.okcoupon.okcoupon.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
@CrossOrigin
@RestController
@RequestMapping("customer")
public class CustomerController extends ClientController {

    @Autowired
    CustomerService customerService;

    @Autowired
    JWT jwt;

    private static final String HEADER = "Authorization";

    /**
     * Post-Method that indicated if the user that try to log in has the permission to use the system
     * by searching in the dataBase the existence of the data that passed to the Method and by compare the Role
     * @param user object that holds the Client-details for each user such id,userName,userPassword,userRole
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws UncompletedFieldsException thrown when passed user-details that missing some part/field
     * @throws UnknownRoleException thrown when passed role that doesn't exist in the system
     */
    @Override
    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody UserDetails user) throws InvalidUserException, UncompletedFieldsException, UnknownRoleException {
        if (user.unCompleteFields()){
            throw new UncompletedFieldsException();
        }
        if (user.unValidRole()){
            throw new UnknownRoleException();
        }
        UserDetails userDetails = UserDetails.builder()
                .id(customerService.login(user.getUserName(), user.getUserPass()))
                .userName(user.getUserName())
                .userPass(user.getUserPass())
                .clientType(user.getClientType())
                .build();
        if (!userDetails.getClientType().equals(ClientType.CUSTOMER.getType())) {
            throw new InvalidUserException();
        }
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(userDetails))
                .body(null);
    }

    /**
     * Post-method (CREATE) that called when the user want to purchase a coupon
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param couponId Integer that indicates which coupon will be purchased, the ID is a generated-key of the coupon
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * @throws NoCouponsLeftException thrown when there are no coupons left, amount is 0
     * @throws SamePurchaseException thrown when the user try to purchase that he already bought in the past
     * @throws ExpiredCouponException thrown when the coupon Date has Expired
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws NotFoundException thrown when user sends an invalid-coupon-id that doesn't exist in the system
     * @throws JWTexpiredException thrown when the Token has expired
     */
    @PostMapping("/newPurchase/{couponId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = HEADER) String token, @PathVariable int couponId) throws NoCouponsLeftException, SamePurchaseException, ExpiredCouponException, InvalidUserException, NotFoundException, JWTexpiredException {
        customerService.purchaseCoupon(customerService.getCustomerDetails(validation(token).getId()), couponId);
        return ResponseEntity.accepted()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(null);
    }
    /**
     * Get-method (READ) that called when the user expect to get all the coupons he purchased
     * @param token token Maximum 256-byte unique JWT Token (String type)
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The list of the coupons passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoCouponPurchasesException thrown when there are no coupons for this company in the system
     */
    @GetMapping("/allCouponsCustomer")
    public ResponseEntity<?> getAllCoupons(@RequestHeader(name = HEADER) String token) throws InvalidUserException, JWTexpiredException, NoCouponPurchasesException {
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(customerService.getCustomerCoupons(validation(token).getId()));
    }
    /**
     * Get-method (READ) that called when the user expect to get all the coupons of the customer filtered by Category
     * @param token token Maximum 256-byte unique JWT Token (String type)
     * @param category enum object that indicates which Category coupons will be filtered by
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The list of the coupons passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws NoCouponsCategoryException thrown when there are no coupons in this chosen Category
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoCouponPurchasesException thrown when there are no coupons for this customer in the system
     */
    @GetMapping("/CustomerCouponsByCategory{category}")
    public ResponseEntity<?> getCouponsByCategory(@RequestHeader(name = HEADER) String token, @PathVariable Category category) throws InvalidUserException, NoCouponsCategoryException, JWTexpiredException, NoCouponPurchasesException {
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(customerService.getCustomerCouponsByCategory(validation(token).getId(), category));

    }
    /**
     * Get-method (READ) that called when the user expect to get all the coupons of the customer filtered by Maximum Price
     * @param token token Maximum 256-byte unique JWT Token (String type)
     * @param price Double number that indicates the maximum price coupons will be filtered by
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The list of the coupons passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws NoCouponsPriceException thrown when there are no coupons up to this chosen Price
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoCouponPurchasesException thrown when there are no coupons for this company in the system
     */
    @GetMapping("/CustomerCouponsByPrice{price}")
    public ResponseEntity<?> getCouponsByMaxPrice(@RequestHeader(name = HEADER) String token, @PathVariable double price) throws InvalidUserException, NoCouponsPriceException, JWTexpiredException, NoCouponPurchasesException {
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(customerService.getCustomerCouponsByPrice(validation(token).getId(), price));

    }
    /**
     * Get-method (READ) that called when the user expect to get the details of specific customer that exists in the system
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The details of the customer passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws NotFoundException thrown when user sends an invalid-id that doesn't exist in the system
     * @throws JWTexpiredException thrown when the Token has expired
     */
    @GetMapping("/customerDetails")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = HEADER) String token) throws InvalidUserException, JWTexpiredException, NotFoundException {
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(customerService.getCustomerDetails(validation(token).getId()));
    }

    /**
     * Get-method (READ) that called when the user expect to get all coupons in the system
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The List of the coupons passed throw the body part
     */
    @GetMapping("/allCouponsInSystem")
    public ResponseEntity<?> getAllCouponsInSystem() {
        return new ResponseEntity<>(customerService.getAllCouponsInSystem(),HttpStatus.ACCEPTED);
    }
    /**
     * private-Method that called everytime when the user try to run each method in the Controller
     * The method verify the User-Details part and the Expiration part
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @return instance of UserDetails specific to the company-user or null
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     */
    private UserDetails validation(String token) throws JWTexpiredException, InvalidUserException {
        try {
            UserDetails userDetails = jwt.validateToken(token);
            if (userDetails.getClientType().equals(ClientType.CUSTOMER.getType())) {
                return userDetails;
            }
        } catch (Exception err) {
            if (err instanceof JWTexpiredException) {
                throw new JWTexpiredException();
            }
            throw new InvalidUserException();
        }
        return null;
    }
}
