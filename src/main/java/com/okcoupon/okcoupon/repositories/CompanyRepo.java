package com.okcoupon.okcoupon.repositories;

import com.okcoupon.okcoupon.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Integer> {
    /**
     * send a query to the dataBase and checks if exists a record in Companies.Table
     * with the email and the password the user send as parameters
     * @param email String value perform the email of the company
     * @param password String value perform the password of the company
     * @return True if the record exists; False if the record doesn't exist
     */
    boolean existsByEmailAndPassword(String email, String password);

    /**
     * send a query to the dataBase and checks if exists a record in Companies.Table
     * with the email and the name the user send as parameters
     * @param email String value perform the email of the company
     * @param name String value perform the name of the company
     * @return True if the record exists; False if the record doesn't exist
     */
    boolean existsByEmailAndName(String email, String name);

    /**
     * send a query to the dataBase and checks if exists a record in Companies.Table
     * with the email the user send as a parameter
     * @param email String value perform the email of the company
     * @return True if the record exists; False if the record doesn't exist
     */
    boolean existsByEmail(String email);

    /**
     * send a query to the dataBase and checks if exists a record in Companies.Table
     * with the name the user send as a parameter
     * @param name String value perform the name of the company
     * @return True if the record exists; False if the record doesn't exist
     */
    boolean existsByName(String name);

    /**
     * send a query to the dataBase and to get a specific record-Company object in Companies.Table
     * with the email and the password the user send as parameters
     * @param email String value perform the email of the company
     * @param password String value perform the password of the company
     * @return an instance of Company.Class initialized by the values of all fields of the required record
     */
    Company findCompanyByEmailAndPassword(String email, String password);

    /**
     * send a query to the dataBase and to get a specific record-Company object in Companies.Table
     * with the name the user send as a parameter
     * @param name String value perform the name of the company
     * @return  an instance of Company.Class initialized by the values of all fields of the required record
     */
    Company getByName(String name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM okcoupon2.companies u WHERE u.id = :id", nativeQuery = true)
    void deleteById(@Param("id") int companyId);
}
