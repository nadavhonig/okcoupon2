package com.okcoupon.okcoupon.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.okcoupon.okcoupon.exceptions.InvalidEmailException;
import com.okcoupon.okcoupon.jasonViews.Views;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "customers",
        indexes = @Index(columnList = "lastName")
)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    private int id;
    @Column(nullable = false, length = 45)
    @JsonView(Views.Public.class)
    private String firstName;
    @Column(nullable = false, length = 45)
    @JsonView(Views.Public.class)
    private String lastName;
    @Column(nullable = false, length = 45)
    @JsonView(Views.Public.class)
    private String email;
    @Column(nullable = false, length = 45)
    @JsonView(Views.Internal.class)
    private String password;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "customer")
    private Set<Purchase> purchases = new HashSet<>();


    /**
     * Public method that indicated if all required fields (name , email , password) are initialized with values that are not null
     * @return True if all required fields are initialized by values which not null; False if some fields are initialized with null value
     */
    public boolean unCompleteFields(){
        return this.getFirstName() == null ||
                this.getLastName() == null ||
                this.getEmail() == null ||
                this.getPassword() == null;
    }

    /**
     * Public method to get the ID of the this.Customer
     * @return Integer perform the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Public method to set the ID of the Customer
     * @param id id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Public method to set the Password of the this.Customer
     * @param password String indicates the password of Customer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Public method to get the Password of the this.Customer
     * @return String indicates the password of this.Customer
     */
    public String getPassword() {
        return password;
    }

    /**
     * Public method to get the first name of the this.Customer
     * @return String indicates the first name of this.Customer
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Private method to set the first name of the this.Customer
     * @param firstName first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Public method to get the last name of the this.Customer
     * @return String indicates the last name of this.Customer
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Private method to set the last name of the this.Customer
     * @param lastName lase name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Public method to set the purchase of the Customer
     * @param purchases purchases to set
     */
    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    /**
     * Public method to get the Email of the this.Customer
     * @return String indicates the email of this.Customer
     */
    public String getEmail() {
        return email;
    }

    /**
     * Public method to set the Email of the this.Customer
     * @param email String indicates the email of Customer
     * @throws InvalidEmailException thrown when user try to set invalid email, without @
     */
    public void setEmail(String email) throws InvalidEmailException {
        if (email.contains("@")) {
            this.email = email;
        } else throw new InvalidEmailException();
    }

    /**
     * Public method to get the purchase of the this.Company
     * @return purchase of the customer
     */

    public Set<Purchase> getPurchases() {
        return purchases;
    }


    /**
     * Public method that compares the one given Object to the Other, based on the data/content of them
     * @param o o other object to compare
     * @return true if the two objects are the same; false if the two objects are not the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email) && Objects.equals(password, customer.password) && Objects.equals(purchases, customer.purchases);
    }

    /**
     * Public method that returns the hashcode value of this.Company
     * @return Integer indicated the unique value in number of this.Customer
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, purchases);
    }

    /**
     * Public methode that returns the value of this.Company given in String format
     * @return String of the object fields and values
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password +
                '}';
    }
}