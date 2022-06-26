package com.okcoupon.okcoupon.service.Interface;

import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;

import java.util.List;

public interface AdminServiceInterface {
    /**
     * Method that indicated if the user that try to log in is exists in the dataBase
     * @param email unique part of the UserDetails performing the User-Name of the company
     * @param password unique part of the UserDetails performing the User-Password of the company
     * @return Integer indicated the Generated-key of the company try to log in, which is the -1 ID value
     */
    int login(String email, String password);
    /**
     * Method that called when the user want to add new coupon to the system

     * @param company instance of Company which holds all the details that company should have
     */
    void addCompany(Company company);
    /**
     * Method that called when the user want to update an exists company
     * @param company instance of Company which holds all the details that company should have
     */
    void updateCompany(Company company);
    /**
     * Method that called when the user want to delete one specific company
     * @param companyId Integer that indicates which company will be deleted, the ID is a generated-key of the company
     */
    void deleteCompany(int companyId);
    /**
     * Method that called when the user expect to get one specific company
     * @param companyId Integer that indicates which company the user chose to get the details about, the ID is a generated-key of the company
     * @return instance of Company which holds all the details that company should have
     */
    Company getOneCompany(int companyId);
    /**
     * Method that called when the user expect to get all the companies in the system
     * @return List of all companies in the system
     */
    List<Company> getAllCompanies();
    /**
     * Method that called when the user want to add new coupon to the system
     * @param customer instance of Customer which holds all the details that customer should have
     */
    void addCustomer(Customer customer);
    /**
     * Method that called when the user want to update an exists customer
     * @param customer instance of Company which holds all the details that customer should have
     */
    void updateCustomer(Customer customer);
    /**
     * Method that called when the user want to delete one specific customer
     * @param customerId Integer that indicates which customer will be deleted, the ID is a generated-key of the customer
     */
    void deleteCustomer(int customerId);
    /**
     * Method that called when the user expect to get one specific customer
     * @param customerId Integer that indicates which customer the user chose to get the details about, the ID is a generated-key of the customer
     * @return instance of Customer which holds all the details that customer should have
     */
    Customer getOneCustomer(int customerId);
    /**
     * Method that called when the user expect to get all the customers in the system
     * @return List of all customers in the system
     */
    List<Customer> getAllCustomers();
    /**
     * Method that called when the user expect to get all coupons of one specific company that exists in the system
     * @param companyId Integer that indicates which company the user chose to get the details about, the ID is a generated-key of the company
     * @return List of all coupons of that company
     */
    List<Coupon> getCompanyCoupons(int companyId);
    /**
     * Method that called when the user expect to get all coupons of one specific customer that exists in the system
     * First it finds a List of all customer's Purchases and Then mapped it to List of Coupons
     * @param customerId Integer that indicates which customer the user chose to get the details about, the ID is a generated-key of the customer
     * @return List of all coupons the customer has purchased in the past
     */
    List<Coupon> getCustomerCoupons(int customerId);
    /**
     * Method that called by the DailyJob.Class once a day, it deletes all the expired coupons in the system
     */
    void deleteExpiredCoupon();
}
