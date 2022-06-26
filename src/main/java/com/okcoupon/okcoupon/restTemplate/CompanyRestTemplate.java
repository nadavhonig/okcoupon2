package com.okcoupon.okcoupon.restTemplate;

import com.okcoupon.okcoupon.auth.ClientType;
import com.okcoupon.okcoupon.auth.UserDetails;
import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
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
import java.util.*;

@Component
@Order(3)


public class CompanyRestTemplate implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    private String token;

    private static final String HEADER = "Authorization";

    private final static String loginUrl = "http://localhost:8080/company/Login";
    private final static String allCouponsUrl = "http://localhost:8080/company/allCouponsCompany";
    private final static String couponsByCategoryUrl = "http://localhost:8080/company/CompanyCouponsByCategory{category}";
    private final static String couponByPriceUrl = "http://localhost:8080/company/CompanyCouponsByPrice{price}";
    private final static String companyDetailsUrl = "http://localhost:8080/company/companyDetails";
    private final static String deleteCouponUrl = "http://localhost:8080/company/deleteCoupon/{id}";
    private final static String newCouponUrl = "http://localhost:8080/company/newCoupon";
    private final static String updateCouponUrl = "http://localhost:8080/company/updateCoupon";

    private HttpEntity<?> getHttpEntity(String token, Object object) {
        HttpEntity<?> httpEntity;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER, this.token);
        if (object instanceof Coupon) {
            httpEntity = new HttpEntity<>(object, httpHeaders);
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
        ResponseEntity<?> object = restTemplate.exchange(loginUrl, HttpMethod.POST, new HttpEntity<>(userDetails), void.class);
        updateToken(object);
        System.out.println(userDetails.getUserName() + " logged through restTemplate " +
                LocalDate.now() + ConsoleColors.PURPLE_BACKGROUND + "\nThe token: " + token + ConsoleColors.RESET);
    }

    private List<Coupon> allCoupons() {
        ResponseEntity<Coupon[]> getAllCoupons = restTemplate.exchange(allCouponsUrl, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class);
        List<Coupon> coupons = Arrays.asList(getAllCoupons.getBody());
        System.out.println(ConsoleColors.CYAN + "all coupon  Of company   through restTemplate");
        coupons.forEach(Coupon::toPrint);
        System.out.print(ConsoleColors.RESET);
        updateToken(getAllCoupons);
        return coupons;
    }

    private List<Coupon> couponsByCategory(Category category) {
        Map<String, String> param = new HashMap<>();
        param.put("category", category.getCategoryType());
        ResponseEntity<Coupon[]> getCouponsByCategory = restTemplate.exchange(couponsByCategoryUrl, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class, param);
        List<Coupon> coupons = Arrays.asList(getCouponsByCategory.getBody());
        System.out.println(ConsoleColors.CYAN + "all coupon  Of company  by category through restTemplate");
        coupons.forEach(Coupon::toPrint);
        System.out.print(ConsoleColors.RESET);
        updateToken(getCouponsByCategory);
        return coupons;
    }

    private List<Coupon> couponsByPrice(int price) {
        Map<String, String> param = new HashMap<>();
        param.put("price", String.valueOf(price));
        ResponseEntity<Coupon[]> getCouponsByPrice = restTemplate.exchange(couponByPriceUrl, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class, param);
        List<Coupon> coupons = Arrays.asList(getCouponsByPrice.getBody());
        System.out.println(ConsoleColors.CYAN + "all coupon  Of company by price through restTemplate");
        coupons.forEach(Coupon::toPrint);
        System.out.print(ConsoleColors.RESET);
        updateToken(getCouponsByPrice);
        return coupons;
    }

    private Company getCompanyDetails() throws ClientErrorException, ServerErrorException {
        ResponseEntity<Company> getCompanyDetails = restTemplate.exchange(companyDetailsUrl, HttpMethod.GET, getHttpEntity(this.token, null), Company.class);
        Company company = getCompanyDetails.getBody();
        System.out.println(ConsoleColors.PURPLE_BRIGHT + "company details through restTemplate\n" + company + ConsoleColors.RESET);
        updateToken(getCompanyDetails);
        return getCompanyDetails.getBody();
    }

    private void addCoupon(Coupon coupon) {
        ResponseEntity<?> object = restTemplate.exchange(newCouponUrl, HttpMethod.POST, getHttpEntity(this.token, coupon), void.class);
        System.out.println(ConsoleColors.CYAN + "coupon " + coupon.getTitle() + " added successfully" + ConsoleColors.RESET);
        updateToken(object);
    }

    private void updateCoupon(Coupon coupon) {
        ResponseEntity<?> object = restTemplate.exchange(updateCouponUrl, HttpMethod.PUT, getHttpEntity(this.token, coupon), void.class);
        System.out.println(ConsoleColors.CYAN + "coupon" + coupon.getId() + " updated successfully" + ConsoleColors.RESET);
        updateToken(object);
    }

    private void deleteCoupon(int id) {
        Map<String, String> param = new HashMap<>();
        param.put("id", String.valueOf(id));
        ResponseEntity<?> object = restTemplate.exchange(deleteCouponUrl, HttpMethod.DELETE, getHttpEntity(this.token, null), void.class, param);
        System.out.println(ConsoleColors.CYAN + "coupon" + id + " deleted successfully" + ConsoleColors.RESET);
        updateToken(object);
    }


    @Override
    public void run(String... args) {

        try {
            UserDetails userDetails = UserDetails.builder()
                    .userName("hyundai@gmail.com")
                    .userPass("1234")
                    .clientType(ClientType.COMPANY.getType()).build();
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
            couponsByCategory(Category.FASHION);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            couponsByPrice(1000);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            getCompanyDetails();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            Coupon coupon = allCoupons().stream().findFirst().get();
            coupon.setTitle("new coupon");
            coupon.setId(0);
            addCoupon(coupon);

        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            Coupon coupon1 = allCoupons().stream().findFirst().get();
            coupon1.setTitle("air pods for you");
            coupon1.setDescription("change change");
            updateCoupon(coupon1);

        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            deleteCoupon(2);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}


