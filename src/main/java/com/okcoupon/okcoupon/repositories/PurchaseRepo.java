package com.okcoupon.okcoupon.repositories;

import com.okcoupon.okcoupon.beans.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Integer> {
    /**
     * send a query to the dataBase and to get a List of records-Purchase objects in Purchase_History.Table
     * with the customerId the user send as a parameter
     * @param customerId Integer value perform the id of the customer which is the Generated-key of customer
     * @return List of purchases,each instance initialized by the values of all fields of each required record
     */
    List<Purchase> findByCustomerId(int customerId);

    /**
     * send a query to the dataBase and to get a List of records-Purchase objects in Purchase_History.Table
     * with the customerId the user send as a parameter
     * @param customerId Integer value perform the id of the customer which is the Generated-key of customer
     * @return Set of purchases,each instance initialized by the values of all fields of each required record
     */
    Set<Purchase> getByCustomerId(int customerId);

    /**
     * send a query to the dataBase and checks if exists a record in Purchase_History.Table
     * with the couponId the user send as a parameter
     * @param couponId Integer value perform the id of the coupon which is the Generated-key of coupon
     * @return true if the purchase exist, false if not
     */
    boolean existsByCouponId(int couponId);

    /**
     * send a DELETE type query to delete a specific record in Purchase_History.Table
     * by the purchaseId the user send as a parameter
     * @param purchaseID Integer value perform the id of the purchase which is the Generated-key of purchase
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM okcoupon2.purchases_history u WHERE u.purchaseID = :purchaseID", nativeQuery = true)
    void deleteByPurchaseId(@Param("purchaseID") int purchaseID);

    /**
     * send a DELETE type query to delete a set of records in Purchase_History.Table
     * by the couponId the user send as a parameter
     * @param couponId Integer value perform the id of the coupon which is the Generated-key of coupon
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM okcoupon2.purchases_history u WHERE u.coupon_id = :coupon_id", nativeQuery = true)
    void deleteByCouponId(@Param("coupon_id") int couponId);
    /**
     * send a DELETE type query to delete a set of records in Purchase_History.Table
     * by the customerId the user send as a parameter
     * @param customerId Integer value perform the id of the customer which is the Generated-key of customer
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM okcoupon2.purchases_history u WHERE u.customer_id = :customer_id", nativeQuery = true)
    void deleteByCustomerId(@Param("customer_id") int customerId);


}
