package com.okcoupon.okcoupon.service;

import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;
import org.assertj.core.api.Assertions;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminServiceUnderTest;

    private Company companyToGet = Company.builder()
            .id(1)
            .name("hyundai")
            .email("hyundai@gmail.com")
            .password("1234")
            .build();

    private Company companyToAdd = Company.builder()
            .name("Yummi dog's food")
            .email("yummi@gmail.com")
            .password("bark")
            .build();

    private Company companyToAddSameName = Company.builder()
            .name("hyundai")
            .email("yummi@gmail.com")
            .password("bark")
            .build();

    private Company companyToUpdateDifferentName = Company.builder()
            .id(1)
            .name("wantToBeKia")
            .email("hyubdaiTheBest@gmail.com")
            .password("1234")
            .build();

    private Customer customerToAdd = Customer.builder()
            .firstName("zeev")
            .lastName("mindali")
            .email("zeev@gmail.com")
            .password("zeev")
            .build();

    private Customer customerToGet = Customer.builder()
            .id(2)
            .firstName("tomer")
            .lastName("shimony")
            .email("tomer@gmail.com")
            .password("1234")
            .build();

    private Customer customerToUpdate = Customer.builder()
            .id(2)
            .firstName("rozan")
            .lastName("shimony")
            .email("rozan@gmail.com")
            .password("1234")
            .build();


    private final int WRONG_ID = 42;

    @Test
    @DisplayName("throws exception when insert invalid userName and password")
    void loginTest() {
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.login("wrongEmail@", "wrongPassword");
        });
        Assertions.assertThat(error.getMessage()).contains("Invalid user details");
    }

    @Test
    @DisplayName("throws exception when insert email already exist")
    void addCompanyTest1() {
        Exception error = assertThrows(Exception.class, ()-> {
            companyToAdd.setEmail("hyundai@gmail.com");
            adminServiceUnderTest.addCompany(companyToAdd);
        });
        Assertions.assertThat(error.getMessage()).contains("email already exists");
    }

    @Test
    @DisplayName("throws exception when insert name already exist")
    void addCompanyTest2() {
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.addCompany(companyToAddSameName);
        });
        Assertions.assertThat(error.getMessage()).contains("name already exists");
    }

    @Test
    @DisplayName("throws exception when company already exist")
    void addCompanyTest3() {
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.addCompany(companyToGet);
        });
        Assertions.assertThat(error.getMessage()).contains("exists in the system");
    }

    @Test
    @DisplayName("throws exception when insert wrong id that don't exist")
    void updateCompanyTest1(){
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.updateCompany(companyToAdd); // no id obtained = 0;
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }

    @Test
    @DisplayName("throws exception when try to update the company's name")
    void updateCompanyTest2(){
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.updateCompany(companyToUpdateDifferentName);
        });
        Assertions.assertThat(error.getMessage()).contains("can't update the name");
    }

    @Test
    @DisplayName("throws exception when insert wrong id that don't exist")
    void deleteCompanyTest(){
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.deleteCompany(WRONG_ID);
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }

    @Test
    @DisplayName("throws exception when insert wrong id that don't exist")
    void getOneCompanyTest(){
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.getOneCompany(WRONG_ID);
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }

    @Test
    @DisplayName("throws exception when company already exist")
    void addCustomerTest() {
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.addCustomer(customerToGet);
        });
        Assertions.assertThat(error.getMessage()).contains("exists in the system");
    }

    @Test
    @DisplayName("throws exception when try to update company's name")
    void updateCompanyTest(){
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.updateCustomer(customerToAdd); // no id obtained = 0;
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }

    @Test
    @DisplayName("throws exception when insert wrong id that don't exist")
    void deleteCustomerTest(){
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.deleteCustomer(WRONG_ID);
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }

    @Test
    @DisplayName("throws exception when insert wrong id that don't exist")
    void getOneCustomerTest(){
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.getOneCustomer(WRONG_ID);
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }

    @Test
    @DisplayName("throws exception when insert wrong id that don't exist")
    void getCompanyCouponsTest(){
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.getCompanyCoupons(WRONG_ID);
        });
        Assertions.assertThat(error.getMessage()).contains("Item can not be found");
    }

    @Test
    @DisplayName("throws exception when insert wrong id that don't exist")
    void getCustomerCouponsTest(){
        Exception error = assertThrows(Exception.class, ()-> {
            adminServiceUnderTest.getCustomerCoupons(WRONG_ID);
        });
        Assertions.assertThat(error.getMessage()).contains("has no purchase");
    }

}