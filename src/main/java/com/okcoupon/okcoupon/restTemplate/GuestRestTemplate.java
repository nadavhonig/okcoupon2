package com.okcoupon.okcoupon.restTemplate;

import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.exceptions.ClientErrorException;
import com.okcoupon.okcoupon.exceptions.ServerErrorException;
import com.okcoupon.okcoupon.utils.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(5)
public class GuestRestTemplate implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    private final static String getAllCouponsInSystem = "http://localhost:8080/guest/allCouponsInSystem";
    private final static String couponsByMaxPrice = "http://localhost:8080/guest/couponsByPrice{price}";
    private final static String couponsByCategory = "http://localhost:8080/guest/couponsByCategory{category}";
    private final static String register = "http://localhost:8080/guest/register";

    private List<Coupon> allCouponsInSystem() {
        ResponseEntity<Coupon[]> getCoupons = restTemplate.getForEntity(getAllCouponsInSystem, Coupon[].class);
        List<Coupon> coupons = Arrays.asList(getCoupons.getBody());
        System.out.println(ConsoleColors.GREEN_BRIGHT + "---------------------HELLO GUEST----------------\n here all coupons in system through restTemplate");
        coupons.forEach(Coupon::toPrint);
        System.out.print(ConsoleColors.RESET);
        return coupons;
    }

    private List<Coupon> couponsByMaxPrice(double price) {
        Map<String, String> param = new HashMap<>();
        param.put("price", String.valueOf(price));
        ResponseEntity<Coupon[]> getCoupons = restTemplate.getForEntity(couponsByMaxPrice, Coupon[].class, param);
        List<Coupon> coupons = Arrays.asList(getCoupons.getBody());
        System.out.println(ConsoleColors.GREEN_BRIGHT + "---------------------HELLO GUEST----------------\n here all coupons by Max Price through restTemplate");
        coupons.forEach(Coupon::toPrint);
        System.out.print(ConsoleColors.RESET);
        return coupons;
    }

    private List<Coupon> couponsByCategory(Category category) {
        Map<String, String> param = new HashMap<>();
        param.put("category", category.getCategoryType());
        ResponseEntity<Coupon[]> getCoupons = restTemplate.getForEntity(couponsByCategory, Coupon[].class, param);
        List<Coupon> coupons = Arrays.asList(getCoupons.getBody());
        System.out.println(ConsoleColors.GREEN_BRIGHT + "---------------------HELLO GUEST----------------\n here all coupons by Category through restTemplate");
        coupons.forEach(Coupon::toPrint);
        System.out.print(ConsoleColors.RESET);
        return coupons;
    }

    private void register(Customer customer) {
        ResponseEntity<?> response = restTemplate.postForEntity(register, customer, void.class);
        System.out.println(ConsoleColors.GREEN_BRIGHT + "---------------------HELLO GUEST----------------\n" + customer + " register through restTemplate");
        System.out.print(ConsoleColors.RESET);

    }

    @Override
    public void run(String... args) throws Exception {
        try {
            allCouponsInSystem();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            couponsByMaxPrice(100);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            couponsByCategory(Category.FASHION);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            register(Customer.builder().email("newCustomer@Hello.com").firstName("Jhon").lastName("Jhonson").password("JJJJ").build());
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}
