package com.okcoupon.okcoupon.restTemplate;

import com.okcoupon.okcoupon.auth.ClientType;
import com.okcoupon.okcoupon.auth.UserDetails;
import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.exceptions.ClientErrorException;
import com.okcoupon.okcoupon.exceptions.InvalidEmailException;
import com.okcoupon.okcoupon.exceptions.ServerErrorException;
import com.okcoupon.okcoupon.utils.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Component
@Order(2)
public class AdministratorRestTemplate implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    private String token;

    private static final String HEADER = "Authorization";


    private final static String login = "http://localhost:8080/administrator/Login";
    private final static String allCompanies = "http://localhost:8080/administrator/allCompanies";
    private final static String allCustomers = "http://localhost:8080/administrator/allCustomers";
    private final static String getCompany = "http://localhost:8080/administrator/getCompany/{id}";
    private final static String getCustomer = "http://localhost:8080/administrator/getCustomer/{id}";
    private final static String companyCoupons = "http://localhost:8080/administrator/getCompanyCoupons/{id}";
    private final static String customerCoupons = "http://localhost:8080/administrator/getCustomerPurchase/{id}";
    private final static String newCompany = "http://localhost:8080/administrator/newCompany";
    private final static String newCustomer = "http://localhost:8080/administrator/newCustomer";
    private final static String updateCompany = "http://localhost:8080/administrator/updateCompany";
    private final static String updateCustomer = "http://localhost:8080/administrator/updateCustomer";
    private final static String deleteCompany = "http://localhost:8080/administrator/deleteCompany/{id}";
    private final static String deleteCustomer = "http://localhost:8080/administrator/deleteCustomer/{id}";

    private HttpEntity<?> getHttpEntity(String token, Object companyOrCustomer) {
        HttpEntity<?> httpEntity;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER, this.token);
        if (companyOrCustomer instanceof Company || companyOrCustomer instanceof Customer) {
            httpEntity = new HttpEntity<>(companyOrCustomer, httpHeaders);
        } else httpEntity = new HttpEntity<>(httpHeaders);
        return httpEntity;
    }

    private void updateToken(ResponseEntity<?> responseEntity) {
        String responseTokenHeader = responseEntity.getHeaders().getFirst(HEADER);
        if (responseTokenHeader != null ) {
            this.token = responseTokenHeader;
        }
    }


    private void login(UserDetails userDetails) {
        ResponseEntity<?> object = restTemplate.exchange(login, HttpMethod.POST, new HttpEntity<>(userDetails), void.class);
       updateToken(object);
        System.out.println(ConsoleColors.PURPLE_BACKGROUND + userDetails.getUserName() + " logged through restTemplate " + LocalDate.now() + "\nThe token: " + this.token + ConsoleColors.RESET);
    }

    private Company getCompany(int id) {
        Map<String, String> param = new HashMap<>();
        param.put("id", String.valueOf(id));
        ResponseEntity<Company> getOneCompany = restTemplate.exchange(getCompany, HttpMethod.GET, getHttpEntity(this.token, null), Company.class, param);
        Company company = getOneCompany.getBody();
        System.out.println(ConsoleColors.BLUE + "company through restTemplate");
        System.out.println(company + ConsoleColors.RESET);
        updateToken(getOneCompany);
        return company;
    }

    private Customer getCustomer(int id) {
        Map<String, String> param = new HashMap<>();
        param.put("id", String.valueOf(id));
        ResponseEntity<Customer> getOneCustomer = restTemplate.exchange(getCustomer, HttpMethod.GET, getHttpEntity(this.token, null), Customer.class, param);
        Customer customer = getOneCustomer.getBody();
        System.out.println(ConsoleColors.BLUE + "customer through restTemplate");
        System.out.println(customer + ConsoleColors.RESET);
        updateToken(getOneCustomer);
        return customer;
    }

    private List<Company> allCompanies() {
        ResponseEntity<Company[]> getAllCompanies = restTemplate.exchange(allCompanies, HttpMethod.GET, getHttpEntity(this.token, null), Company[].class);
        List<Company> companies = Arrays.asList(getAllCompanies.getBody());
        System.out.println(ConsoleColors.BLUE + "all companies through restTemplate");
        companies.forEach(System.out::println);
        System.out.println(ConsoleColors.RESET);
        updateToken(getAllCompanies);
        return companies;
    }

    private List<Customer> allCustomers() {
        ResponseEntity<Customer[]> getAllCustomers = restTemplate.exchange(allCustomers, HttpMethod.GET, getHttpEntity(this.token, null), Customer[].class);
        List<Customer> customers = Arrays.asList(getAllCustomers.getBody());
        System.out.println(ConsoleColors.BLUE + "all customers through restTemplate");
        customers.forEach(System.out::println);
        System.out.println(ConsoleColors.RESET);
        updateToken(getAllCustomers);
        return customers;
    }

    private void customerCoupons(int id) {
        Map<String, String> param = new HashMap<>();
        param.put("id", String.valueOf(id));
        ResponseEntity<Coupon[]> getCustomerCoupons = restTemplate.exchange(customerCoupons, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class, param);
        List<Coupon> customerCoupons = Arrays.asList(getCustomerCoupons.getBody());
        System.out.println(ConsoleColors.BLUE + "customer coupons through restTemplate");
        customerCoupons.forEach(Coupon::toPrint);
        System.out.println(ConsoleColors.RESET);
        updateToken(getCustomerCoupons);
    }

    private List<Coupon> companyCoupons(int id) {
        Map<String, String> param = new HashMap<>();
        param.put("id", String.valueOf(id));
        ResponseEntity<Coupon[]> getCompanyCoupons = restTemplate.exchange(companyCoupons, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class, param);
        List<Coupon> companyCoupons = Arrays.asList(getCompanyCoupons.getBody());
        System.out.println(ConsoleColors.BLUE + "company coupons through restTemplate");
        companyCoupons.forEach(Coupon::toPrint);
        System.out.println(ConsoleColors.RESET);
        updateToken(getCompanyCoupons);
        return companyCoupons;
    }

    private void addCompany(Company company) {
        ResponseEntity<?> response = restTemplate.exchange(newCompany, HttpMethod.POST, getHttpEntity(this.token, company), void.class);
        System.out.println(ConsoleColors.BLUE + company + "added successfully through restTemplate" + ConsoleColors.RESET);
        updateToken(response);
    }

    private void addCustomer(Customer customer) {
        ResponseEntity<?> object = restTemplate.exchange(newCustomer, HttpMethod.POST, getHttpEntity(this.token, customer), void.class);
        System.out.println(ConsoleColors.BLUE + customer + "added successfully through restTemplate" + ConsoleColors.RESET);
        updateToken(object);
    }

    private void updateCompany(Company company) {
        ResponseEntity<?> object = restTemplate.exchange(updateCompany, HttpMethod.PUT, getHttpEntity(this.token, company), void.class);
        System.out.println(ConsoleColors.BLUE + company + "updated successfully through restTemplate" + ConsoleColors.RESET);
        updateToken(object);
    }

    private void updateCustomer(Customer customer) {
        ResponseEntity<?> object = restTemplate.exchange(updateCustomer, HttpMethod.PUT, getHttpEntity(this.token, customer), void.class);
        System.out.println(ConsoleColors.BLUE + customer + "updated successfully through restTemplate" + ConsoleColors.RESET);
        updateToken(object);
    }

    private void deleteCompany(int id) {
        Map<String, String> param = new HashMap<>();
        param.put("id", String.valueOf(id));
        ResponseEntity<?> object = restTemplate.exchange(deleteCompany, HttpMethod.DELETE, getHttpEntity(this.token, null), void.class, param);
        System.out.println(ConsoleColors.BLUE + "company  " + id + " deleted successfully through restTemplate" + ConsoleColors.RESET);
        updateToken(object);
    }

    private void deleteCustomer(int id) {
        Map<String, String> param = new HashMap<>();
        param.put("id", String.valueOf(id));
        ResponseEntity<?> object = restTemplate.exchange(deleteCustomer, HttpMethod.DELETE, getHttpEntity(this.token, null), void.class, param);
        System.out.println(ConsoleColors.BLUE + "customer  " + id + " deleted successfully through restTemplate" + ConsoleColors.RESET);
        updateToken(object);
    }

    @Override
    public void run(String... args) {
        try {
            UserDetails userDetails = UserDetails.builder()
                    .userName("admin@admin.com")
                    .userPass("admin")
                    .clientType(ClientType.ADMIN.getType()).build();
            login(userDetails);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            allCompanies();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            allCustomers();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
        try {
            getCompany(4);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            getCustomer(1);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            addCompany(Company.builder().email("taltul@gmail.com").name("tal").password("0000").build());
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            addCustomer(Customer.builder().email("barakush@gmail.com").firstName("barak").lastName("ush").password("okCoupon").build());
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            Company company = getCompany(6);
            company.setPassword("deleteMe");
            company.setEmail("byeTal@");
            updateCompany(company);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            Customer customer = getCustomer(6);
            customer.setPassword("1234");
            customer.setEmail("byeBarak@");
            updateCustomer(customer);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
/*
        try {
            deleteCompany(6);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
*/
        try {
            deleteCustomer(6);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            companyCoupons(1);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            customerCoupons(1);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}



