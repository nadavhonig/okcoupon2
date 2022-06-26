package com.okcoupon.okcoupon.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
    @JsonIgnore
    private int id;
    private String userName;
    private String userPass;
    private String clientType;

    /**
     * Public method that indicates if the clientType is valid
     * @return True if the clientType is one of the values - ADMIN, CUSTOMER, COMPANY; False if the clientType is not one of the values describes
     */
    public boolean unValidRole(){
        return !this.getClientType().equals(ClientType.ADMIN.getType()) &&
                !this.getClientType().equals(ClientType.CUSTOMER.getType()) &&
                !this.getClientType().equals(ClientType.COMPANY.getType());
    }
    /**
     * Public method that indicated if all required fields (name , email , password) are initialized with values that are not null
     * @return True if all required fields are initialized by values which not null; False if some fields are initialized with null value
     */
    public boolean unCompleteFields(){
        return this.getUserName() == null ||
                this.getUserPass() == null ||
                this.getClientType() == null;
    }
}



