package com.okcoupon.okcoupon.service;

import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.beans.Purchase;
import com.okcoupon.okcoupon.exceptions.*;
import com.okcoupon.okcoupon.repositories.CompanyRepo;
import com.okcoupon.okcoupon.repositories.CouponRepo;
import com.okcoupon.okcoupon.repositories.CustomerRepo;
import com.okcoupon.okcoupon.repositories.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private PurchaseRepo purchaseRepo;
    @Autowired
    private CouponRepo couponRepo;

    /**
     * Method that indicated if the user that try to log in is exists in the dataBase
     * @param email unique part of the UserDetails performing the User-Name of the company
     * @param password unique part of the UserDetails performing the User-Password of the company
     * @return Integer indicated the Generated-key of the company try to log in, which is the -1 ID value
     * @throws InvalidUserException thrown when passed invalid user-details
     */
    public int login(String email, String password) throws InvalidUserException {
        if(email.equals("admin@admin.com") && password.equals("admin")) {
            return -1;
        }
        throw new InvalidUserException();
    }

    /**
     * Method that called when the user want to add new coupon to the system
     * It runs some conditions that check if:
     *                There is already a company with the UserDetails (email,password) in the system
     *                There is already a company with the same Username which is the email in the system
     *                There is already a company with the same name in the system
     * @param company instance of Company which holds all the details that company should have
     * @throws DuplicateItemException thrown when user try to add company that already exists in the system
     * @throws DuplicateNameException thrown when user try to add company with name that already exists  in the system
     * @throws DuplicateEmailException thrown when user try to add company with email that already exists  in the system
     */
    public void addCompany(Company company) throws DuplicateItemException, DuplicateNameException, DuplicateEmailException {
        if (!companyRepo.existsByEmailAndName(company.getEmail(), company.getName())) {
            if (!companyRepo.existsByName(company.getName())) {
                if (!companyRepo.existsByEmail(company.getEmail())) {
                    companyRepo.saveAndFlush(company);
                } else throw new DuplicateEmailException();
            } else throw new DuplicateNameException();
        } else throw new DuplicateItemException();
    }

    /**
     * Method that called when the user want to update an exists company
     * @param company instance of Company which holds all the details that company should have
     * @throws NotFoundException thrown when user sends an invalid-company-id that doesn't exist in the system
     * @throws UpdateNameException thrown when the user try to update the name of the company
     */
    public void updateCompany(Company company) throws NotFoundException, UpdateNameException {
        if (companyRepo.existsById(company.getId())) {
            String oldName = companyRepo.findById(company.getId()).get().getName();
            if (oldName.equals(company.getName())) {
                companyRepo.saveAndFlush(company);
            } else throw new UpdateNameException();
        } else throw new NotFoundException();

    }
    /**
     * Method that called when the user want to delete one specific company
     * @param companyId Integer that indicates which company will be deleted, the ID is a generated-key of the company
     * @throws NotFoundException thrown when user sends an invalid-company-id that doesn't exist in the system
     */
    public void deleteCompany(int companyId) throws NotFoundException {
        if (companyRepo.existsById(companyId)) {
            List<Coupon> coupons = couponRepo.findByCompanyId(companyId);
            companyRepo.deleteById(companyId);
            couponRepo.deleteByCompanyId(companyId);
            coupons.forEach(coupon -> purchaseRepo.deleteByCouponId(coupon.getId()));
        } else throw new NotFoundException();
    }
    /**
     * Method that called when the user expect to get one specific company
     * @param companyId Integer that indicates which company the user chose to get the details about, the ID is a generated-key of the company
     * @return instance of Company which holds all the details that company should have
     * @throws NotFoundException thrown when user sends an invalid-customer-id that doesn't exist in the system
     * @throws NoCompaniesException thrown when there are no companies in the system
     */
    public Company getOneCompany(int companyId) throws NotFoundException, NoCompaniesException {
        if (companyRepo.count()==0){
            throw new NoCompaniesException();
        }
        if (companyRepo.existsById(companyId)) {
            return companyRepo.getById(companyId);
        }
        throw new NotFoundException();
    }

    /**
     * Method that called when the user expect to get all the companies in the system
     * @return List of all companies in the system
     * @throws NoCompaniesException thrown when there are no companies in the system
     */
    public List<Company> getAllCompanies() throws NoCompaniesException {
        if (companyRepo.count()!=0){
            return companyRepo.findAll();
        }
        throw new NoCompaniesException();
    }

    /**
     * Method that called when the user want to add new coupon to the system
     * It runs oen condition and checks if there is already a customer with the same Username which is the email in the system
     * @param customer instance of Customer which holds all the details that customer should have
     * @throws DuplicateItemException thrown when user try to add customer that already exists in the system
     */
    public void addCustomer(Customer customer) throws DuplicateItemException {
        if (!customerRepo.existsByEmail(customer.getEmail())) {
            customerRepo.saveAndFlush(customer);
        } else throw new DuplicateItemException();
    }
    /**
     * Method that called when the user want to update an exists customer
     * @param customer instance of Company which holds all the details that customer should have
     * @throws NotFoundException thrown when user sends an invalid-customer-id that doesn't exist in the system
     */
    public void updateCustomer(Customer customer) throws NotFoundException {
        if (customerRepo.existsById(customer.getId())) {
            customerRepo.saveAndFlush(customer);
        } else throw new NotFoundException();
    }
    /**
     * Method that called when the user want to delete one specific customer
     * @param customerId Integer that indicates which customer will be deleted, the ID is a generated-key of the customer
     * @throws NotFoundException thrown when user sends an invalid-customer-id that doesn't exist in the system
     */
    public void deleteCustomer(int customerId) throws NotFoundException {
        if (customerRepo.existsById(customerId)) {
            purchaseRepo.deleteByCustomerId(customerId);
            customerRepo.deleteById(customerId);
        } else throw new NotFoundException();
    }
    /**
     * Method that called when the user expect to get one specific customer
     * @param customerId Integer that indicates which customer the user chose to get the details about, the ID is a generated-key of the customer
     * @return instance of Customer which holds all the details that customer should have
     * @throws NotFoundException thrown when user sends an invalid-customer-id that doesn't exist in the system
     * @throws NoCustomersException thrown when there are no companies in the system
     */
    public Customer getOneCustomer(int customerId) throws NotFoundException, NoCustomersException {
        if (customerRepo.count()==0){
            throw new NoCustomersException();
        }
        if (customerRepo.existsById(customerId)) {
            return customerRepo.getById(customerId);
        }
        throw new NotFoundException();
    }
    /**
     * Method that called when the user expect to get all the customers in the system
     * @return List of all customers in the system
     * @throws NoCustomersException thrown when there are no customers in the system
     */
    public List<Customer> getAllCustomers() throws NoCustomersException {
        if (customerRepo.count()!=0) {
            return customerRepo.findAll();
        }
        throw new NoCustomersException();
    }

    /**
     * Method that called when the user expect to get all coupons of one specific company that exists in the system
     * @param companyId Integer that indicates which company the user chose to get the details about, the ID is a generated-key of the company
     * @return List of all coupons of that company
     * @throws NotFoundException thrown when user sends an invalid-company-id that doesn't exist in the system
     */
    public List<Coupon> getCompanyCoupons(int companyId) throws NotFoundException {
        if (companyRepo.existsById(companyId)) {
            return (List<Coupon>) companyRepo.findById(companyId).get().getCoupons();
        }
        throw new NotFoundException();
    }

    /**
     * Method that called when the user expect to get all coupons of one specific customer that exists in the system
     * First it finds a List of all customer's Purchases and Then mapped it to List of Coupons
     * @param customerId Integer that indicates which customer the user chose to get the details about, the ID is a generated-key of the customer
     * @return List of all coupons the customer has purchased in the past
     * @throws NoCouponPurchasesException thrown when there are no coupons for this company in the system
     */
    public List<Coupon> getCustomerCoupons(int customerId) throws  NoCouponPurchasesException {
        if (customerRepo.existsById(customerId)) {
            List<Purchase> purchases = purchaseRepo.findByCustomerId(customerId);
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
        throw new NoCouponPurchasesException();
    }

    /**
     * Method that called by the DailyJob.Class once a day, it deletes all the expired coupons in the system
     */
    public void deleteExpiredCoupon() {
        couponRepo.eraseCoupon();
    }
}
