package com.okcoupon.okcoupon.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.okcoupon.okcoupon.auth.ClientType;
import com.okcoupon.okcoupon.auth.JWT;

import com.okcoupon.okcoupon.auth.UserDetails;
import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.exceptions.*;
import com.okcoupon.okcoupon.jasonViews.Views;
import com.okcoupon.okcoupon.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("company")
public class CompanyController extends ClientController {

    @Autowired
    CompanyService companyService;

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
                .id(companyService.login(user.getUserName(), user.getUserPass()))
                .userName(user.getUserName())
                .userPass(user.getUserPass())
                .clientType(user.getClientType())
                .build();
        if (!userDetails.getClientType().equals(ClientType.COMPANY.getType())) {
            throw new InvalidUserException();
        }
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(userDetails))
                .body(null);
    }

    /**
     * Get-method (READ) that called when the user expect to get the details of specific company that exists in the system
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The details of the company passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws NotFoundException thrown when user sends an invalid-id that doesn't exist in the system
     * @throws JWTexpiredException thrown when the Token has expired
     */
    @GetMapping("/companyDetails")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = HEADER) String token) throws InvalidUserException, NotFoundException, JWTexpiredException {
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(companyService.getCompanyDetails(validation(token).getId()));
    }

    /**
     * Get-method (READ) that called when the user expect to get all the coupons of the company
     * @param token token Maximum 256-byte unique JWT Token (String type)
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The list of the coupons passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoCouponsCompanyException thrown when there are no coupons for this company in the system
     * @throws NotFoundException thrown when user sends an invalid-id that doesn't exist in the system
     */
    @GetMapping("/allCouponsCompany")
    public ResponseEntity<?> getAllCoupons(@RequestHeader(name = HEADER) String token) throws InvalidUserException, JWTexpiredException, NoCouponsCompanyException, NotFoundException {
        return ResponseEntity.ok()
                .header("Authorization", jwt.generateToken(validation(token)))
                .body(companyService.getCompanyCoupons(validation(token).getId()));
    }

    /**
     * Get-method (READ) that called when the user expect to get all the coupons of the company filtered by Category
     * @param token token Maximum 256-byte unique JWT Token (String type)
     * @param category enum object that indicates which Category coupons will be filtered by
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The list of the coupons passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws NoCouponsCategoryException thrown when there are no coupons in this chosen Category
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoCouponsCompanyException thrown when there are no coupons for this company in the system
     */
    @GetMapping("/CompanyCouponsByCategory{category}")
    public ResponseEntity<?> getCouponsByCategory(@RequestHeader(name = HEADER) String token, @PathVariable Category category) throws InvalidUserException, NoCouponsCategoryException, JWTexpiredException, NoCouponsCompanyException {
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(companyService.getCouponByCategory(validation(token).getId(), category));
    }

    /**
     * Get-method (READ) that called when the user expect to get all the coupons of the company filtered by Maximum Price
     * @param token token Maximum 256-byte unique JWT Token (String type)
     * @param price Double number that indicates the maximum price coupons will be filtered by
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The list of the coupons passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws NoCouponsPriceException thrown when there are no coupons up to this chosen Price
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoCouponsCompanyException thrown when there are no coupons for this company in the system
     */
    @GetMapping("/CompanyCouponsByPrice{price}")
    public ResponseEntity<?> getCouponsByMaxPrice(@RequestHeader(name = HEADER) String token, @PathVariable double price) throws InvalidUserException, NoCouponsPriceException, JWTexpiredException, NoCouponsCompanyException {
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(companyService.getCouponByMaxPrice(validation(token).getId(), price));
    }

    /**
     * Post-method (CREATE) that called when the user want to add new coupon to the system
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param coupon instance of Coupon which holds all the details that coupon should have
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-ACCEPTED
     * @throws DuplicateItemException thrown when user try to add coupon that already exists with the same in the system
     * @throws ExpiredCouponException thrown when user try to add coupon with Expired-Date
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws NotFoundException thrown when company doesn't exist in the system
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws UncompletedFieldsException thrown when passed coupon-object that missing some part/field
     * @throws UnknownCategoryException thrown when passed coupon-object with unKnown Category that doesn't exist
     * @throws  CompanyNameMismatchException thrown when sends an invalid-company-name
     */
    @PostMapping("/newCoupon")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> addCoupon(@RequestHeader(name = HEADER) String token, @RequestBody Coupon coupon) throws
            DuplicateItemException, ExpiredCouponException, InvalidUserException, NotFoundException, JWTexpiredException, UncompletedFieldsException, UnknownCategoryException, CompanyNameMismatchException {
        coupon.setCompany(companyService.getCompanyDetails(validation(token).getId()));
        coupon.setCompanyName(coupon.getCompany().getName());
        if (coupon.unCompleteFields()){
            throw new UncompletedFieldsException();
        }
        if (coupon.validCategory()) {
            throw new UnknownCategoryException();
        }
        coupon.setCompanyName(companyService.getCompanyDetails(validation(token).getId()).getName());
        companyService.addCoupon(coupon);
        return ResponseEntity.accepted()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(null);
    }

    /**
     * Put-method (UPDATE) that called when the user want to update an exists coupon
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param coupon instance of Coupon which holds all the details coupon should have
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-ACCEPTED
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws CouponNotFoundException thrown when user sends an invalid-coupon-id that doesn't exist in the system
     * @throws NotFoundException thrown when company doesn't exist in the system
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoPermissionException thrown when company try to update other company's coupon
     * @throws UncompletedFieldsException thrown when passed coupon-object that missing some part/field
     * @throws UnknownCategoryException thrown when passed coupon-object with unKnown Category that doesn't exist
     * @throws CompanyNameMismatchException thrown when sends an invalid-company-name
     *
     */
    @PutMapping("/updateCoupon")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = HEADER) String token, @RequestBody Coupon coupon) throws InvalidUserException, CouponNotFoundException, NotFoundException, JWTexpiredException, NoPermissionException, UncompletedFieldsException, UnknownCategoryException, CompanyNameMismatchException {
        if (validation(token).getId() != companyService.getOneCoupon(coupon.getId()).getCompany().getId()) {
            throw new NoPermissionException();
        }
        coupon.setCompany(companyService.getCompanyDetails(validation(token).getId()));
        coupon.setCompanyName(coupon.getCompany().getName());
        if (coupon.unCompleteFields()){
            throw new UncompletedFieldsException();
        }
        if (coupon.validCategory()){
            throw new UnknownCategoryException();
        }
        companyService.updateCoupon(coupon);
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(null);
    }

    /**
     * Delete-method (DELETE) that called when the user want to delete one specific coupon
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param id Integer that indicates which coupon will be deleted, the ID is a generated-key of the coupon
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * @throws NotFoundException thrown when user sends an invalid-coupon-id that doesn't exist in the system
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoPermissionException thrown when company try to delete other company's coupon
     */
    @DeleteMapping("/deleteCoupon/{id}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = HEADER) String token, @PathVariable int id) throws NotFoundException, InvalidUserException, JWTexpiredException, NoPermissionException {
        if (validation(token).getId() != companyService.getOneCoupon(id).getCompany().getId()) {
            throw new NoPermissionException();
        }
        companyService.deleteCoupon(id);
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(null);
    }

    /**
     * private-Method that called everytime when the user try to run each method in the Controller
     * The method verify the User-Details part and the Expiration part
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @return instance of UserDetails specific to the company-user or null
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     */
    private UserDetails validation(String token) throws InvalidUserException, JWTexpiredException {
        try {
            UserDetails userDetails = jwt.validateToken(token);
            if (userDetails.getClientType().equals(ClientType.COMPANY.getType())) {
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
