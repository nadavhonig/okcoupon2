package com.okcoupon.okcoupon.repositories;

import com.okcoupon.okcoupon.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    /**
     * send a query to the dataBase and checks if exists a record in Customers.Table
     * with the email the user send as a parameter
     * @param email String value perform the email of the customer
     * @return True if the record exists; False if the record doesn't exist
     */
    boolean existsByEmail(String email);
    /**
     * send a query to the dataBase and checks if exists a record in Customers.Table
     * with the email and the password the user send as parameters
     * @param email String value perform the email of the customer
     * @param password String value perform the password of the customer
     * @return True if the record exists; False if the record doesn't exist
     */
    boolean existsByEmailAndPassword(String email, String password);
    /**
     * send a query to the dataBase and to get a specific record-customer object in Customer.Table
     * with the email and the password the user send as parameters
     * @param email String value perform the email of the customer
     * @param password String value perform the password of the customer
     * @return an instance of Customer.Class initialized by the values of all fields of the required record
     */
    Customer findCustomerByEmailAndPassword(String email, String password);
}
