package com.okcoupon.okcoupon.service;

import com.okcoupon.okcoupon.beans.*;
import com.okcoupon.okcoupon.repositories.CouponRepo;
import com.okcoupon.okcoupon.repositories.CustomerRepo;
import com.okcoupon.okcoupon.repositories.PurchaseRepo;
import org.hibernate.query.criteria.internal.predicate.ImplicitNumericExpressionTypeDeterminer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertThrows;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerServiceUnderTest;
    @Autowired
    private CouponRepo couponRepo;
    @Autowired
    private PurchaseRepo purchaseRepo;
    @Autowired
    private CustomerRepo customerRepo;

    private Customer customerUnderTest = Customer.builder()
            .id(2)
            .firstName("tomer")
            .lastName("shimony")
            .email("tomer@gmail.com")
            .password("1234")
            .build();

    public Customer zeevNoCoupons = Customer.builder()
            .id(6)
            .firstName("zeevNoCoupons")
            .lastName("mindali")
            .email("zeev@gmail.com")
            .password("zeev")
            .build();

    private final int CUSTOMER_NO_PURCHASE = 6;
    private final int TRY_BUY_ME_TWICE = 7; //need to be (6-10) for this customer
    private final int WRONG_ID = 42;

    @Test
    @DisplayName("throws exception when insert invalid userName and password")
    void loginTest() {
        Exception error = assertThrows(Exception.class, ()-> {
            customerServiceUnderTest.login("wrongEmail@", "wrongPassword");
        });
        Assertions.assertThat(error.getMessage()).contains("Invalid user details");
    }

    @Test
    @DisplayName("throws exception when insert wrong id to purchase coupon")
    void purchaseCouponTest1(){
        Exception error = assertThrows(Exception.class, ()-> {
            customerServiceUnderTest.purchaseCoupon(customerUnderTest,WRONG_ID);
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }


    @Test
    @DisplayName("throws exception when insert coupon_id that expired")
    void purchaseCouponTest2(){
        Exception error = assertThrows(Exception.class, ()-> {
            Coupon couponExpired = couponRepo.getByTitle("mcdonald's").get(0);
            couponExpired.setEndDate(Date.valueOf(LocalDate.now().minusYears(1)));
            couponExpired = couponRepo.save(couponExpired);
            customerServiceUnderTest.purchaseCoupon(customerServiceUnderTest.getCustomerDetails(customerUnderTest.getId()) , couponRepo.getByTitle("mcdonald's").get(0).getId());
        });
        Assertions.assertThat(error.getMessage()).contains("coupon has passed");
    }

    @Test
    @DisplayName("throws exception when insert coupon_id that out of stash")
    void purchaseCouponTest3(){
        Exception error = assertThrows(Exception.class, ()-> {
            customerServiceUnderTest.purchaseCoupon(customerUnderTest , couponRepo.getByTitle("macBook Air m1").get(0).getId());
        });
        Assertions.assertThat(error.getMessage()).contains("No coupons left");
    }

    @Test
    @DisplayName("throws exception when insert coupon_id that customer already bought before")
    void purchaseCouponTest4(){
        this.customerUnderTest.setPurchases(purchaseRepo.getByCustomerId(2));
        Exception error = assertThrows(Exception.class, ()-> {
            customerServiceUnderTest.purchaseCoupon(customerUnderTest , TRY_BUY_ME_TWICE);
        });
        Assertions.assertThat(error.getMessage()).contains("bought this coupon before");
    }

    @Test
    @DisplayName("throws exception when insert customer_id with no purchases" )
    void getCustomerCouponsTest() {
        Exception error = assertThrows(Exception.class, ()-> {
            customerServiceUnderTest.getCustomerCoupons(CUSTOMER_NO_PURCHASE);
        });
        Assertions.assertThat(error.getMessage()).contains("has no purchase");
    }

    @Test
    @DisplayName("throws exception when insert non-match category" )
    void getCustomerCouponsByCategoryTest1() {
        Exception error = assertThrows(Exception.class, ()-> {
            customerServiceUnderTest.getCustomerCouponsByCategory(customerUnderTest.getId(),Category.RESTAURANT);
        });
        Assertions.assertThat(error.getMessage()).contains("try another category");
    }

    @Test
    @DisplayName("throws exception when no coupons for this customer" )
    void getCustomerCouponsByCategoryTest2() {
        Exception error = assertThrows(Exception.class, ()-> {
            customerServiceUnderTest.getCustomerCouponsByCategory(zeevNoCoupons.getId(),Category.RESTAURANT);
        });
        Assertions.assertThat(error.getMessage()).contains("buy any coupon");
    }

    @Test
    @DisplayName("throws exception when insert non-match number" )
    void getCustomerCouponsByPriceTest1() {
        Exception error = assertThrows(Exception.class, ()-> {
            customerServiceUnderTest.getCustomerCouponsByPrice(customerUnderTest.getId(),0);
        });
        Assertions.assertThat(error.getMessage()).contains("try bigger price");
    }

    @Test
    @DisplayName("throws exception when insert non-match number" )
    void getCustomerCouponsByPriceTest2() {
        Exception error = assertThrows(Exception.class, ()-> {
            customerServiceUnderTest.getCustomerCouponsByPrice(zeevNoCoupons.getId(),9);
        });
        Assertions.assertThat(error.getMessage()).contains("buy any coupon");
    }

    @Test
    @DisplayName("throws exception when insert wrong id that don't exist")
    void getOneCustomerTest(){
        Exception error = assertThrows(Exception.class, ()-> {
            customerServiceUnderTest.getCustomerDetails(WRONG_ID);
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }
}