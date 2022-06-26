package com.okcoupon.okcoupon.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.okcoupon.okcoupon.auth.JWT;
import com.okcoupon.okcoupon.auth.UserDetails;

import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.jasonViews.Views;
import com.okcoupon.okcoupon.exceptions.*;
import com.okcoupon.okcoupon.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.ElementType;

@CrossOrigin
@RestController
@RequestMapping("administrator")
public class AdminController extends ClientController {

    @Autowired
    AdminService adminService;

    @Autowired
    JWT jwt;

    @Autowired
    ApplicationContext ctx;

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
        if (user.unCompleteFields()) {
            throw new UncompletedFieldsException();
        }
        if (user.unValidRole()) {
            throw new UnknownRoleException();
        }
        UserDetails userAdmin = ctx.getBean("admin", UserDetails.class);
        if (adminService.login(user.getUserName(), user.getUserPass()) != userAdmin.getId() &&
                !user.getClientType().equals(userAdmin.getClientType())) {
            throw new InvalidUserException();
        }
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(userAdmin))
                .body(null);
    }

    /**
     * Get-method (READ) that called when the user expect to get all the companies in the system
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The list of the companies passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoCompaniesException thrown when there are no companies in the system
     */
    @GetMapping("/allCompanies")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = HEADER) String token) throws
            InvalidUserException, JWTexpiredException, NoCompaniesException {
        validation(token);
        UserDetails userDetails = validation(token);
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(userDetails))
                .body(adminService.getAllCompanies());
    }
    /**
     * Get-method (READ) that called when the user expect to get all the customers in the system
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The list of the customers passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoCustomersException thrown when there are no customers in the system
     */
    @GetMapping("/allCustomers")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = HEADER) String token) throws
            InvalidUserException, JWTexpiredException, NoCustomersException {
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(adminService.getAllCustomers());
    }
    /**
     * Get-method (READ) that called when the user expect to get one specific company that exists in the system
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param id Integer that indicates which company will be passed, the ID is a generated-key of the company
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The details of the company passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoCompaniesException thrown when there are no companies in the system
     * @throws NotFoundException thrown when user sends an invalid-id that doesn't exist in the system
     */
    @GetMapping("/getCompany/{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = HEADER) String token, @PathVariable int id) throws
            NotFoundException, InvalidUserException, JWTexpiredException, NoCompaniesException {
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(adminService.getOneCompany(id));
    }
    /**
     * Get-method (READ) that called when the user expect to get one specific customer that exists in the system
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param id Integer that indicates which customer will be passed, the ID is a generated-key of the customer
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The details of the customer passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoCustomersException thrown when there are no customers in the system
     * @throws NotFoundException thrown when user sends an invalid-id that doesn't exist in the system
     */
    @GetMapping("/getCustomer/{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = HEADER) String token,
                                            @PathVariable int id) throws NotFoundException, InvalidUserException, JWTexpiredException, NoCustomersException {
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(adminService.getOneCustomer(id));
    }

    /**
     * Get-method (READ) that called when the user expect to get all coupons of one specific company that exists in the system
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param id Integer that indicates which customer will be passed, the ID is a generated-key of the company
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The list of the coupon's company passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NotFoundException thrown when user sends an invalid-id that doesn't exist in the system
     */
    @GetMapping("/getCompanyCoupons/{id}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = HEADER) String token, @PathVariable int id) throws InvalidUserException, JWTexpiredException, NotFoundException {
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(adminService.getCompanyCoupons(id));
    }
    /**
     * Get-method (READ) that called when the user expect to get all purchases of one specific customer that exists in the system
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param id Integer that indicates which customer will be passed, the ID is a generated-key of the customer
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The list of the purchase's customer passed throw the body part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws NoCouponPurchasesException thrown when the customer has no purchases
     */
    @GetMapping("/getCustomerPurchase/{id}")
    public ResponseEntity<?> getCustomerPurchase(@RequestHeader(name = HEADER) String token, @PathVariable int id) throws InvalidUserException, JWTexpiredException, NoCouponPurchasesException {
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(adminService.getCustomerCoupons(id));
    }

    /**
     * Post-method (CREATE) that called when the user want to add new company to the system
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param company instance of Company which holds all the details that company should have
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-ACCEPTED
     * @throws DuplicateItemException thrown when user try to add company that already exists in the system
     * @throws DuplicateNameException thrown when user try to add company with NAME that already exists in the system
     * @throws DuplicateEmailException thrown when user try to add company with EMAIL that already exists in the system
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws UncompletedFieldsException thrown when passed company-object that missing some part/field
     */
    @PostMapping("/newCompany")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> addCompany(@RequestHeader(name = HEADER) String token, @RequestBody Company company) throws
            DuplicateItemException, DuplicateNameException, DuplicateEmailException, InvalidUserException, JWTexpiredException, UncompletedFieldsException {
        if (company.unCompleteFields()) {
            throw new UncompletedFieldsException();
        }
        adminService.addCompany(company);
        return ResponseEntity.accepted()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(null);
    }

    /**
     * Post-method (CREATE) that called when the user want to add new customer to the system
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param customer instance of Customer which holds all the details that customer should have
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-ACCEPTED
     * @throws DuplicateItemException thrown when user try to add customer that already exists in the system
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws UncompletedFieldsException thrown when passed customer-object that missing some part/field
     */
    @PostMapping("/newCustomer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> addCustomer(@RequestHeader(name = HEADER) String token, @RequestBody Customer customer) throws
            DuplicateItemException, InvalidUserException, JWTexpiredException, UncompletedFieldsException {
        if (customer.unCompleteFields()) {
            throw new UncompletedFieldsException();
        }
        adminService.addCustomer(customer);
        return ResponseEntity.accepted()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(null);
    }

    /**
     * Put-method (UPDATE) that called when the user want to update an exists company
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param company instance of Company which holds all the details company should have
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-ACCEPTED
     * @throws NotFoundException thrown when user sends an invalid-id that doesn't exist in the system
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws UpdateNameException thrown when the user try to update the name of the company
     * @throws UncompletedFieldsException thrown when passed company-object that missing some part/field
     */
    @PutMapping("/updateCompany")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateCompany(@RequestHeader(name = HEADER) String token, @RequestBody Company company) throws
            NotFoundException, InvalidUserException, JWTexpiredException, UpdateNameException, UncompletedFieldsException {
        if (company.unCompleteFields()) {
            throw new UncompletedFieldsException();
        }
        company.setCoupons(adminService.getCompanyCoupons(company.getId()));
        adminService.updateCompany(company);
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(null);
    }

    /**
     * Put-method (UPDATE) that called when the user want to update an exists customer
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param customer instance of Company which holds all the details customer should have
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * @throws NotFoundException thrown when user sends an invalid-id that doesn't exist in the system
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     * @throws InvalidEmailException thrown when the user try to update to an invalid email (without @)
     * @throws NoCustomersException thrown when there are no customers in the system
     * @throws UncompletedFieldsException thrown when passed customer-object that missing some part/field
     */
    @PutMapping("/updateCustomer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = HEADER) String token, @RequestBody Customer customer) throws
            NotFoundException, InvalidUserException, JWTexpiredException, InvalidEmailException, NoCustomersException, UncompletedFieldsException {
        customer.setPurchases(adminService.getOneCustomer(customer.getId()).getPurchases());
        if (customer.unCompleteFields()) {
            throw new UncompletedFieldsException();
        }
        adminService.updateCustomer(customer);
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(null);
    }

    /**
     * Delete-method (DELETE) that called when the user want to delete one specific company
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param id Integer that indicates which customer will be deleted, the ID is a generated-key of the company
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * @throws NotFoundException thrown when user sends an invalid-id that doesn't exist in the system
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     */
    @DeleteMapping("/deleteCompany/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = HEADER) String token, @PathVariable int id) throws
            NotFoundException, InvalidUserException, JWTexpiredException {
        adminService.deleteCompany(id);
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(null);
    }

    /**
     * Delete-method (DELETE) that called when the user want to delete one specific customer
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @param id Integer that indicates which customer will be deleted, the ID is a generated-key of the customer
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * @throws NotFoundException thrown when user sends an invalid-id that doesn't exist in the system
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     */
    @DeleteMapping("/deleteCustomer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = HEADER) String token, @PathVariable int id) throws
            NotFoundException, InvalidUserException, JWTexpiredException {
        adminService.deleteCustomer(id);
        return ResponseEntity.ok()
                .header(HEADER, jwt.generateToken(validation(token)))
                .body(null);
    }

    /**
     * private-Method that called everytime when the user try to run each method in the Controller
     * The method verify the User-Details part and the Expiration part
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @return instance of UserDetails specific to Admin-user or null
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws JWTexpiredException thrown when the Token has expired
     */
    private UserDetails validation(String token) throws InvalidUserException, JWTexpiredException {
        try {
            UserDetails adminUserDetails = ctx.getBean("admin", UserDetails.class);
            if (jwt.validateToken(token).getClientType().equals(adminUserDetails.getClientType())) {
                return adminUserDetails;
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

