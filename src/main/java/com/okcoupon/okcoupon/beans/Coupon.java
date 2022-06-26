package com.okcoupon.okcoupon.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.okcoupon.okcoupon.jasonViews.Views;
import com.okcoupon.okcoupon.utils.TablePrinter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.swing.text.View;
import java.sql.Date;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "coupons",
        indexes = @Index(columnList = "title")
)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    public int id;
    @ManyToOne
    @JsonIgnore
    Company company;
    @Column(nullable = false, length= 40)
    private String companyName;
    @Column(nullable = false, length = 40)
    private Category category;
    @Column(nullable = false, length = 45)
    private String title;
    @Column(nullable = false, length = 45)
    private String description;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Date endDate;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String image;
    @JsonIgnore
    @OneToMany(mappedBy = "coupon", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Purchase> purchases = new HashSet<>();

    public Coupon(int id, Company company,String companyName, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.id = id;
        this.company = company;
        this.companyName = companyName;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    public Coupon(Company company,String companyName, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.company = company;
        this.companyName = companyName;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Public method that indicated if all required fields (name , email , password) are initialized with values that are not null
     *
     * @return True if all required fields are initialized by values which not null; False if some fields are initialized with null value
     */

    public boolean unCompleteFields() {
        return this.getTitle() == null ||
                this.getDescription() == null ||
                this.getCategory() == null ||
                this.getPrice() == 0 ||
                this.getAmount() == 0 ||
                this.getImage() == null ||
                this.getStartDate() == null ||
                this.getEndDate() == null;
    }

    public boolean validCategory() {
        return !this.category.equals(Category.FASHION) &&
                !this.category.equals(Category.ENTERTAINMENT) &&
                !this.category.equals(Category.RESTAURANT) &&
                !this.category.equals(Category.ELECTRICITY) &&
                !this.category.equals(Category.VACATION);
    }

    /**
     * Public method to get the ID of the this.Coupon
     *
     * @return Integer perform the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Public method to set the ID of the coupon
     * @param id the id of the coupon to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Public method to get the company of the this.Coupon
     *
     * @return company of the coupon
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Public method to set the company of the coupon
     * @param company company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Public method to get the category of the this.Coupon
     *
     * @return Enum of the category which the coupon belong to
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Public method to set the category of the coupon
     * @param category category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Public method to get the title of the this.Coupon
     *
     * @return String of the coupon title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Public method to set the title of the coupon
     * @param title title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
         * Public method to get the description of the this.Coupon
     *
     * @return String of the coupon description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Public method to set the title of the description
     * @param description description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Public method to get the start date of the this.Coupon
     *
     * @return Date of the start of the coupon
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Public method to set the start date of the coupon
     * @param startDate set start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Public method to get the end date of the this.Coupon
     *
     * @return Date of the expiration of the coupon
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Public method to set the end date of the coupon
     * @param endDate end date to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Public method to get the amount of the this.Coupon
     *
     * @return Integer indicates the quantity of the coupon
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Public method to set the purchase of the coupon
     * @param  purchases purchases to set
     */
    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    /**
     * Public method to set the amount of the coupon
     * @param amount amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Public method to get the price of the this.Coupon
     *
     * @return Double indicates the price of the coupon
     */
    public double getPrice() {
        return price;
    }

    /**
     * Public method to set the price of the coupon
     * @param price price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Public method to get the image of the this.Coupon
     *
     * @return String of the image address
     */
    public String getImage() {
        return image;
    }

    /**
     * Public method to set the image of the coupon
     * @param image image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Public method to set the purchase of the coupon
     * @return Set of purchases
     */
    public Set<Purchase> getPurchases() {
        return purchases;
    }


    /**
     * Public method that compares the one given Object to the Other, based on the data/content of them
     * @param o other object to compare
     * @return true if the two objects are the same; false if the two objects are not the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id && amount == coupon.amount && Double.compare(coupon.price, price) == 0 && Objects.equals(company, coupon.company) && Objects.equals(companyName, coupon.companyName) && category == coupon.category && Objects.equals(title, coupon.title) && Objects.equals(description, coupon.description) && Objects.equals(startDate, coupon.startDate) && Objects.equals(endDate, coupon.endDate) && Objects.equals(image, coupon.image) && Objects.equals(purchases, coupon.purchases);
    }

    /**
     * Public method that returns the hashcode value of this.Coupon
     * @return Integer indicated the unique value in number of this.Coupon
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, company, companyName, category, title, description, startDate, endDate, amount, price, image, purchases);
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image +
                '}';
    }

    public void toPrint() {
        TablePrinter.print(Coupon.builder()
                .id(this.id)
                .companyName(this.companyName)
                .title(this.title)
                .description(this.description)
                .amount(this.amount)
                .price(this.price)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .category(this.category)
                .image("")
                .purchases(null)
                .company(null)
                .build());
    }
}
