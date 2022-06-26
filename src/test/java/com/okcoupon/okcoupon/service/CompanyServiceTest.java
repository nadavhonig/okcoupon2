package com.okcoupon.okcoupon.service;

import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.repositories.CompanyRepo;
import com.okcoupon.okcoupon.repositories.CouponRepo;
import com.okcoupon.okcoupon.repositories.PurchaseRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompanyServiceTest {

    @Autowired
    private CompanyService companyServiceUnderTest;
    @Autowired
    private CouponRepo couponRepo;
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private PurchaseRepo purchaseRepo;

    private Company companyUnderTest = Company.builder()
            .id(1)
            .name("hyundai")
            .email("hyundai@gmail.com")
            .password("1234")
            .build();

    private Company companyNoCoupons = Company.builder()
            .id(0)
            .name("no Coupons for you")
            .email("poorCompany@gmail.com")
            .password("000")
            .build();

    public Coupon expiredNewCoupon = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("the shop is close")
            .description("we will back after the holiday")
            .startDate(Date.valueOf(LocalDate.now().minusDays(30)))
            .endDate(Date.valueOf(LocalDate.now().minusDays(20)))
            .price(99)
            .image(":)))")
            .amount(0)
            .company(companyUnderTest)
            .companyName(companyUnderTest.getName())
            .build();

    private final int COMPANY_NO_COUPONS = 6;
    private final int TRY_BUY_ME_TWICE = 7; //need to be (6-10) for this customer
    private final int WRONG_ID = 42;

    @Test
    @DisplayName("throws exception when insert invalid userName and password")
    void loginTest() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyServiceUnderTest.login("wrongEmail@", "wrongPassword");
        });
        Assertions.assertThat(error.getMessage()).contains("Invalid user details");
    }

    @Test
    @DisplayName("throws exception when company already exist")
    void addCouponTest1() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyServiceUnderTest.addCoupon(couponRepo.getById(1));
        });
        Assertions.assertThat(error.getMessage()).contains("exists in the system");
    }

    @Test
    @DisplayName("throws exception when coupon expiration date has passed")
    void addCouponTest2() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyServiceUnderTest.addCoupon(expiredNewCoupon);
        });
        Assertions.assertThat(error.getMessage()).contains("coupon has passed");
    }

    @Test
    @DisplayName("throws exception throw exception when try to update company's name")
    void updateCouponTest() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyServiceUnderTest.updateCoupon(new Coupon()); // no id obtained = 0;
        });
        Assertions.assertThat(error.getMessage()).contains("No coupon found");
    }

    @Test
    @DisplayName("throws exception when insert wrong id that don't exist")
    void deleteCouponTest() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyServiceUnderTest.deleteCoupon(WRONG_ID);
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }

    @Test
    @DisplayName("throws exception when insert wrong id that don't exist")
    void getCompanyCouponsTest() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyServiceUnderTest.getCompanyCoupons(WRONG_ID);
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }

    @Test
    @DisplayName("throws exception when insert company with no coupons")
    void getCompanyCouponsTest2() {
        companyRepo.save(companyNoCoupons);
        Exception error = assertThrows(Exception.class, ()-> {
            companyServiceUnderTest.getCompanyCoupons(companyRepo.getByName("no Coupons for you").getId());
        });
        Assertions.assertThat(error.getMessage()).contains("Please add a new coupon");

        companyRepo.deleteById(companyRepo.getByName("no Coupons for you").getId());
    }

    @Test
    @DisplayName("throws exception when insert wrong id that don't exist")
    void getOneCouponTest() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyServiceUnderTest.getOneCoupon(WRONG_ID);
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }

    @Test
    @DisplayName("throws exception when insert non-match category" )
    void getCouponByCategoryTest() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyServiceUnderTest.getCouponByCategory(companyUnderTest.getId(), Category.DOGFOOD);
        });
        Assertions.assertThat(error.getMessage()).contains("try another category");
    }

    @Test
    @DisplayName("throws exception when insert company with no coupons" )
    void getCustomerCouponsByCategoryTest2() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyRepo.saveAndFlush(companyNoCoupons);
            companyServiceUnderTest.getCouponByCategory(companyRepo.getByName("no Coupons for you").getId(),Category.FASHION);
        });
        Assertions.assertThat(error.getMessage()).contains("Please add a new coupon");
        companyRepo.deleteById(companyRepo.getByName("no Coupons for you").getId());
    }

    @Test
    @DisplayName("throws exception when insert non-match number" )
    void getCouponByMaxPriceTest1() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyServiceUnderTest.getCouponByMaxPrice(companyUnderTest.getId(),1);
        });
        Assertions.assertThat(error.getMessage()).contains("try bigger price");
    }

    @Test
    @DisplayName("throws exception when insert company with no coupons" )
    void getCompanyCouponsByPriceTest2() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyRepo.saveAndFlush(companyNoCoupons);
            companyServiceUnderTest.getCouponByMaxPrice(companyRepo.getByName("no Coupons for you").getId(),999);
        });
        Assertions.assertThat(error.getMessage()).contains("Please add a new coupon");
        companyRepo.deleteById(companyRepo.getByName("no Coupons for you").getId());
    }

    @Test
    @DisplayName("throws exception when insert wrong id that don't exist")
    void getCompanyDetailsTest() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyServiceUnderTest.getCompanyDetails(WRONG_ID);
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }
}
