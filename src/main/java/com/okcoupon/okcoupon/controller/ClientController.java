package com.okcoupon.okcoupon.controller;

import com.okcoupon.okcoupon.auth.UserDetails;
import com.okcoupon.okcoupon.exceptions.InvalidUserException;
import com.okcoupon.okcoupon.exceptions.UncompletedFieldsException;
import com.okcoupon.okcoupon.exceptions.UnknownRoleException;
import org.springframework.http.ResponseEntity;

public abstract class ClientController {
    /**
     * Abstract-method that need to be in-use for each Client-Type (Admin, Company, Customer)
     * The first method that need to call when the client start to use the system, indicate if the user has permission to use the system
     * @param userDetails object that holds the Client-details for each user such id,userName,userPassword,userRole
     * @return ResponseEntity with unique Token sends via the header part
     * @throws InvalidUserException thrown when passed invalid user-details
     * @throws UncompletedFieldsException thrown when passed user-details that missing some part/field
     * @throws UnknownRoleException thrown when passed role that doesn't exist in the system
     */
    public abstract ResponseEntity<?> login(UserDetails userDetails) throws InvalidUserException, UncompletedFieldsException, UnknownRoleException;
}
