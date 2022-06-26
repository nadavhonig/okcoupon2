package com.okcoupon.okcoupon.repositories;

import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    /**
     * send a query to the dataBase and to get a List of records-Coupon objects in Coupons.Table
     * with the Category the user send as a parameter
     * @param category enum type that perform the category of the coupons
     * @return List of coupons,each instance initialized by the values of all fields of each required record
     */
    List<Coupon> findByCategory(Category category);
    /**
     * send a query to the dataBase and to get a List of records-Coupon objects in Coupons.Table
     * with the  Maximum price the user send as a parameter
     * @param price Double value that perform the Maximum price of the coupons
     * @return List of coupons,each instance initialized by the values of all fields of each required record
     */
    List<Coupon> findByPriceLessThan(double price);
    /**
     * send a query to the dataBase and to get a List of records-Coupon objects in Coupons.Table
     * with the companyId and the Category the user send as parameters
     * @param companyId Integer value perform the id of the company which is the Generated-key of company
     * @param category enum type that perform the category of the coupons
     * @return List of coupons,each instance initialized by the values of all fields of each required record
     */
    List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);

    /**
     * send a query to the dataBase and to get a List of records-Coupon objects in Coupons.Table
     * with the companyId and the Maximum price the user send as parameters
     * @param companyId Integer value perform the id of the company which is the Generated-key of company
     * @param price Double value that perform the Maximum price of the coupons
     * @return List of coupons,each instance initialized by the values of all fields of each required record
     */
    List<Coupon> findByCompanyIdAndPriceLessThan(int companyId, double price);

    /**
     * send a query to the dataBase and to get a List of records-Coupon objects in Coupons.Table
     * with the companyId the user send as a parameter
     * @param companyId Integer value perform the id of the company which is the Generated-key of company
     * @return List of coupons,each instance initialized by the values of all fields of each required record
     */
    List<Coupon> findByCompanyId(int companyId);

    /**
     * send a query to the dataBase and checks if exists a record in Coupons.Table
     * with the title and the companyId the user send as parameters
     * @param title String value perform the title of the company
     * @param companyId Integer value perform the id of the company which is the Generated-key of company
     * @return True if the record exists; False if the record doesn't exist
     */
    boolean existsByTitleAndCompanyId(String title, int companyId);

    /**
     * send a query to the dataBase and to get a List of records-Coupon objects in Coupons.Table
     * with the title the user send as a parameter
     * @param title String value perform the title of the company
     * @return List of coupons,each instance initialized by the values of all fields of each required record
     */
    List<Coupon> getByTitle(String title);

    /**
     * send a DELETE type query to delete a specific record in Coupons.Table
     * by the couponId the user send as a parameter
     * @param couponId Integer value perform the id of the coupon which is the Generated-key of coupon
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM okcoupon2.coupons u WHERE u.id = :id", nativeQuery = true)
    void deleteById(@Param("id") int couponId);
    /**
     * send a DELETE type query to delete all records that holds specific companyId in Coupons.Table
     * by the companyId the user send as a parameter
     * @param companyId Integer value perform the id of the company which is the Generated-key of company
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM okcoupon2.coupons u WHERE u.company_id = :company_id", nativeQuery = true)
    void deleteByCompanyId(@Param("company_id") int companyId);


    /**
     * send a DELETE type query to delete a set of records in Coupons.Table
     * deletes all coupons that the Date value in field end_date is before today's Date
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM okcoupon2.coupons WHERE DATE(end_date)<CURDATE()", nativeQuery = true)
    void eraseCoupon();

}
