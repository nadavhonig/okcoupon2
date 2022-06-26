package com.okcoupon.okcoupon.configuration;

import com.okcoupon.okcoupon.auth.ClientType;
import com.okcoupon.okcoupon.auth.UserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersConfiguration {
    @Bean
    public UserDetails admin() {
        UserDetails admin = new UserDetails();
        admin.setId(-1);
        admin.setUserName("admin@admin.com");
        admin.setUserPass("admin");
        admin.setClientType(ClientType.ADMIN.getType());
        return admin;
    }
}
