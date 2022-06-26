package com.okcoupon.okcoupon.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.okcoupon.okcoupon.exceptions.InvalidEmailException;
import com.okcoupon.okcoupon.jasonViews.Views;
import io.swagger.annotations.ApiParam;
import lombok.*;
import javax.persistence.*;
import java.util.*;




@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder


@Table(
        name = "companies",
        indexes = @Index(columnList = "name")
)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    private int id;
    @Column(nullable = false, length = 45)
    @JsonView(Views.Public.class)
    private String name;
    @Column(nullable = false, length = 45)
    @JsonView(Views.Public.class)
    private String email;
    @Column(nullable = false, length = 45)
    @JsonView(Views.Internal.class)
    private String password;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "company")
    private Collection<Coupon> coupons = new HashSet<>();

    /**
     * Public method that indicated if all required fields (name , email , password) are initialized with values that are not null
     * @return True if all required fields are initialized by values which not null; False if some fields are initialized with null value
     */
    public boolean unCompleteFields(){
        return this.getName() == null ||
                this.getPassword() == null ||
                this.getEmail() == null;
    }

    /**
     * Public method to get the ID of the this.Company
     * @return Integer perform the ID
     */
    public int getId() {
        return id;
    }
    /**
     * Private method to set the ID of the Company
     */
    private void setId(int id) {
        this.id = id;
    }

    /**
     * Private method to set the Name of the this.Company
     * @param name String indicates the name of Company
     */
    private void setName(String name) {
            this.name = name;
    }

    /**
     * Public method to get the ID of the this.Company
     * @return String indicates the name of this.Company
     */
    public String getName() {
        return name;
    }

    /**
     * Public method to get the Email of the this.Company
     * @return String indicates the email of this.Company
     */
    public String getEmail() {
        return email;
    }

    /**
     * Public method to set the Email of the this.Company
     * @param email String indicates the email of Company
     * @throws InvalidEmailException thrown when user try to set invalid email, without @
     */
    public void setEmail(String email) throws InvalidEmailException {
        if (email.contains("@")) {
            this.email = email;
        } else throw new InvalidEmailException();
    }

    /**
     * Public method to set the Collection of Coupons of the Company
     * @param coupons Collection of Coupon.Class
     */
    public void setCoupons(Collection<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     * Public method to set the Password of the this.Company
     * @param password String indicates the password of Company
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Public method to get the Password of the this.Company
     * @return String indicates the password of this.Company
     */
    @ApiParam(access="hide")
    public String getPassword() {
        return password;
    }

    /**
     * Public method to get the Collection of Coupons of the Company
     * @return Collection of Coupons of the this.Company
     */
    public Collection<Coupon> getCoupons() {
        return coupons;
    }

    /**
     * Public method that compares the one given Object to the Other, based on the data/content of them
     * @param  o other project to compare
     * @return true if the two objects are the same; false if the two objects are not the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id && Objects.equals(name, company.name) && Objects.equals(email, company.email) && Objects.equals(password, company.password);
    }

    /**
     * Public method that returns the hashcode value of this.Company
     * @return Integer indicated the unique value in number of this.Company
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }

    /**
     * Public methode that returns the value of this.Company given in String format
     * @return String of the objects fields and values
     */
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

