package com.okcoupon.okcoupon.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "purchases_history")
public class Purchase {
    @Id
    @GeneratedValue
    private int purchaseID;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Coupon coupon;

    public Purchase(Customer customer, Coupon coupon) {
        this.customer = customer;
        this.coupon = coupon;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public int getPurchaseID() {
        return purchaseID;
    }


    @Override
    public String toString() {
        return "Purchase_Id: " + getPurchaseID();
    }
}
