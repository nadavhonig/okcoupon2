package com.okcoupon.okcoupon.advise;

import com.fasterxml.jackson.core.JsonParseException;
import com.okcoupon.okcoupon.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CouponSystemAdvise {
    /**
     * method invoked when Invalid-User-Exception has thrown
     * response status returns from the server is UNAUTHORIZED
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {InvalidUserException.class})
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorDetail invalidUserMessage() {
        return new ErrorDetail("Invalid user! ", "please try again or call your administrator");
    }

    /**
     * method invoked when Not-Found-Exception has thrown
     * response status returns from the server is NOT_FOUND
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorDetail notFoundMessage() {
        return new ErrorDetail("Not exists! ", "Item can not be found, please try again with different details");
    }

    /**
     * method invoked when Duplicate-Item-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {DuplicateItemException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail duplicateItemMessage() {
        return new ErrorDetail("Bad request, duplicate details! ", "Already exists in the system");
    }

    /**
     * method invoked when Duplicate-Name-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {DuplicateNameException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail duplicateNameMessage() {
        return new ErrorDetail("Bad request, duplicate company name! ", "This name already exists in the system, Please try another one");
    }

    /**
     * method invoked when Duplicate-Name-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {DuplicateEmailException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail duplicateEmailMessage() {
        return new ErrorDetail("Bad request, duplicate Email! ", "This email already exists in system, Please try another one");
    }

    /**
     * method invoked when Update-Id-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {UpdateIDException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail updateIdMessage() {
        return new ErrorDetail("Bad request, it's illegal to update the id! ", "You can't update the ID, it's illegal");
    }
    /**
     * method invoked when Update-Name-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {UpdateNameException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail updateNameMessage() {
        return new ErrorDetail("Bad request, it's illegal to update the name! ", "You can't update the name, it's illegal");
    }
    /**
     * method invoked when Invalid-Email-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {InvalidEmailException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail invalidEmailMessage() {
        return new ErrorDetail("Bad request, invalid email! ", "Invalid email");
    }
    /**
     * method invoked when Expired-Coupon-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {ExpiredCouponException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail expiresCouponMessage() {
        return new ErrorDetail("Bad request, coupon is expired! ", "The date of coupon has passed");
    }
    /**
     * method invoked when No-Coupon-Category-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCouponsCategoryException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail noCouponsByCategoryMessage() {
        return new ErrorDetail("Sorry! ", "No coupons in of this category type. please try another category");
    }
    /**
     * method invoked when No-Coupon-Price-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCouponsPriceException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail noCouponsByPriceMessage() {
        return new ErrorDetail("Sorry, try other price! ", "No coupons below this price type. please try bigger price");
    }
    /**
     * method invoked when Coupon-Not-Found-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {CouponNotFoundException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail couponNotFoundMessage() {
        return new ErrorDetail("Sorry! ", "No coupon found");
    }
    /**
     * method invoked when No-Coupons-Left-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCouponsLeftException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail noCouponsMessage() {
        return new ErrorDetail("Sorry No Coupons! ", "No coupons left");
    }
    /**
     * method invoked when Same-Purchase-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {SamePurchaseException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail samePurchaseMessage() {
        return new ErrorDetail("Sorry you can't buy this coupon! ", "you already bought this coupon before...");
    }
    /**
     * method invoked when No-Coupon-Purchase-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCouponPurchasesException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail NoPurchaseMessage() {
        return new ErrorDetail("You didn't buy anything! ", "Customer has no purchase, Please buy any coupon and try again");
    }
    /**
     * method invoked when No-Coupon-Company-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCouponsCompanyException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail NoCouponsCompanyMessage() {
        return new ErrorDetail("No Coupons! ", "Please add a new coupon and try again");
    }
    /**
     * method invoked when No-Coupon-Customer-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCouponsCustomerException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail NoCouponsCustomerMessage() {
        return new ErrorDetail("No Coupons! ", "Please buy at least one coupon and try again");
    }
    /**
     * method invoked when No-Companies-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCompaniesException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail NoCompaniesMessage() {
        return new ErrorDetail("No Companies in the system! ", "Please add a new company and try again");
    }
    /**
     * method invoked when No-Customers-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCustomersException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail NoCustomersMessage() {
        return new ErrorDetail("No Customers in the system! ", "Please add a new customer and try again");
    }
    /**
     * method invoked when No-Permission-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoPermissionException.class})
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ErrorDetail NoPermissionMessage() {
        return new ErrorDetail("Access denied!", "You have no permission to do it, please call your administrator");
    }
    /**
     * method invoked when Uncompleted-Fields-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {UncompletedFieldsException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail UncompletedFieldsException() {
        return new ErrorDetail("Uncompleted fields!", "You have not complete all the fields, please try again");
    }
    /**
     * method invoked when Unknown-Role-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {UnknownRoleException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail UnknownRoleException(){
        return new ErrorDetail("Bad Input!", "Unknown Role. please try again");
    }
    /**
     * method invoked when Unknown-Category-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {UnknownCategoryException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail unknownCategoryException(){
        return new ErrorDetail("Bad Input!", "Unknown Category. please try again");
    }

    /**
     * method invoked when Json-Parse-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {JsonParseException.class})
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorDetail jsonParseException(){
        return new ErrorDetail("Bad input!", "please check the content and verify all fields written properly with write punctuation");
    }
    /**
     * method invoked when JWT-Expired-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {JWTexpiredException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail jwtExpiredException(){
        return new ErrorDetail("Time has passed", "You didn't do anything for a long time, please login again");
    }
    @ExceptionHandler(value = {CompanyNameMismatchException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail companyNameMismatchException(){
        return new ErrorDetail("Error in Company Name!", "Please check the spelling of company name in form");
    }
}
