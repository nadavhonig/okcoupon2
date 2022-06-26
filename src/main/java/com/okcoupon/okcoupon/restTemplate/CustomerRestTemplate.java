package com.okcoupon.okcoupon.restTemplate;

import com.okcoupon.okcoupon.auth.ClientType;
import com.okcoupon.okcoupon.auth.UserDetails;
import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.exceptions.ClientErrorException;
import com.okcoupon.okcoupon.exceptions.ServerErrorException;
import com.okcoupon.okcoupon.utils.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(4)
public class CustomerRestTemplate implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    private String token;

    private static final String HEADER = "Authorization";

    private final static String login = "http://localhost:8080/customer/Login";
    private final static String newPurchase = "http://localhost:8080/customer/newPurchase/{couponId}";
    private final static String allCoupons = "http://localhost:8080/customer/allCouponsCustomer";
    private final static String couponsByCategory = "http://localhost:8080/customer/CustomerCouponsByCategory{category}";
    private final static String couponsByPrice = "http://localhost:8080/customer/CustomerCouponsByPrice{price}";
    private final static String customerDetails = "http://localhost:8080/customer/customerDetails";
    private final static String getAllCouponsInSystem = "http://localhost:8080/customer/allCouponsInSystem";

    private HttpEntity<?> getHttpEntity(String token, Object couponOrCustomer) {
        HttpEntity<?> httpEntity;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER, this.token);
        if (couponOrCustomer instanceof Coupon || couponOrCustomer instanceof Customer) {
            httpEntity = new HttpEntity<>(couponOrCustomer, httpHeaders);
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
        System.out.println(ConsoleColors.PURPLE_BACKGROUND + userDetails.getUserName() + " logged through restTemplate " + LocalDate.now() + "\nThe token: " + token + ConsoleColors.RESET);
    }

    private void newPurchase(int couponId) {
        Map<String, String> param = new HashMap<>();
        param.put("couponId", String.valueOf(couponId));
        ResponseEntity<Void> object = restTemplate.exchange(newPurchase, HttpMethod.POST, getHttpEntity(this.token, null), void.class, param);
        System.out.println(ConsoleColors.PURPLE_BRIGHT + "coupon " + couponId + " successfully purchased for " + getCustomerDetails().getFirstName() + " through restTemplate" + ConsoleColors.RESET);
        updateToken(object);
    }

    private List<Coupon> allCoupons() {
        ResponseEntity<Coupon[]> getAllCoupons = restTemplate.exchange(allCoupons, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class);
        List<Coupon> coupons = Arrays.asList(getAllCoupons.getBody());
        System.out.println(ConsoleColors.PURPLE_BRIGHT + "all coupons of customer through restTemplate");
        coupons.forEach(Coupon::toPrint);
        System.out.println(ConsoleColors.RESET);
        updateToken(getAllCoupons);
        return coupons;
    }

    private List<Coupon> couponsByCategory(Category category) {
        Map<String, String> param = new HashMap<>();
        param.put("category", category.getCategoryType());
        ResponseEntity<Coupon[]> getCouponsByCategory = restTemplate.exchange(couponsByCategory, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class, param);
        List<Coupon> coupons = Arrays.asList(getCouponsByCategory.getBody());
        System.out.println(ConsoleColors.PURPLE_BRIGHT + "all coupon  Of customer  by category through restTemplate");
        coupons.forEach(Coupon::toPrint);
        System.out.print(ConsoleColors.RESET);
        updateToken(getCouponsByCategory);
        return coupons;
    }

    private List<Coupon> couponsByPrice(int price) {
        Map<String, String> param = new HashMap<>();
        param.put("price", String.valueOf(price));
        ResponseEntity<Coupon[]> getCouponsByPrice = restTemplate.exchange(couponsByPrice, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class, param);
        List<Coupon> coupons = Arrays.asList(getCouponsByPrice.getBody());
        System.out.println(ConsoleColors.PURPLE_BRIGHT + "all coupon  Of customer  by price through restTemplate");
        coupons.forEach(Coupon::toPrint);
        System.out.print(ConsoleColors.RESET);
        updateToken(getCouponsByPrice);
        return coupons;
    }

    private Customer getCustomerDetails() {
        ResponseEntity<Customer> getDetails = restTemplate.exchange(customerDetails, HttpMethod.GET, getHttpEntity(this.token, null), Customer.class);
        Customer customer = getDetails.getBody();
        System.out.println(ConsoleColors.PURPLE_BRIGHT + "customer details through restTemplate\n" + customer + ConsoleColors.RESET);
        updateToken(getDetails);
        return customer;
    }

    private List<Coupon> allCouponsInSystem() {
        ResponseEntity<Coupon[]> getCoupons = restTemplate.getForEntity(getAllCouponsInSystem, Coupon[].class);
        List<Coupon> coupons = Arrays.asList(getCoupons.getBody());
        System.out.println(ConsoleColors.PURPLE_BRIGHT + "all coupons in system through restTemplate");
        coupons.forEach(Coupon::toPrint);
        System.out.print(ConsoleColors.RESET);
        return coupons;
    }

    @Override
    public void run(String... args) {

        try {
            UserDetails userDetails = UserDetails.builder()
                    .userName("tomer@gmail.com")
                    .userPass("1234")
                    .clientType(ClientType.CUSTOMER.getType()).build();
            login(userDetails);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            allCoupons();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            couponsByCategory(Category.ELECTRICITY);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            couponsByPrice(400);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            getCustomerDetails();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            newPurchase(1);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            allCouponsInSystem();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}

