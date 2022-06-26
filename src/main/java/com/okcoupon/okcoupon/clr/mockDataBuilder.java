package com.okcoupon.okcoupon.clr;

import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.service.AdminService;
import com.okcoupon.okcoupon.service.CompanyService;
import com.okcoupon.okcoupon.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;


@Component
@Order(1)
public class mockDataBuilder implements CommandLineRunner {
    @Autowired
    AdminService adminService;
    @Autowired
    CompanyService companyService;
    @Autowired
    CustomerService customerService;

    private int setEndDate() {
        return (int) ((Math.random() * 50) + 15);
    }

    private int setStartDate() {
        return (int) ((Math.random() * 5));
    }


    private int amountSetter() {
        return (int) (Math.random() * 20 + 5);
    }


    /// companies:///
    public Company hyundai = Company.builder()
            .name("hyundai")
            .email("hyundai@gmail.com")
            .password("1234")
            .build();
    public Company gucci = Company.builder()
            .name("gucci")
            .email("gucci@gmail.com")
            .password("12345")
            .build();
    public Company ibm = Company.builder()
            .name("ibm")
            .email("ibm@gmail.com")
            .password("123456")
            .build();
    public Company facebook = Company.builder()
            .name("facebook")
            .email("facebook@gmail.com")
            .password("1234567")
            .build();
    public Company google = Company.builder()
            .name("google")
            .email("google@walla.com")
            .password("12345678")
            .build();

    ///coupons///


    public Coupon tvHouse = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("airpods")
            .description("airpods pro")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(487)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAlQmPVcD5R81QgtqwiOJTmwb1Wd0CIQ0L14Ne4qVrrGVA5zD23Eq-tYxhUlO2lX8YIPQ&usqp=CAU")
            .amount(amountSetter())
            .company(hyundai)
            .companyName(hyundai.getName())
            .build();


    public Coupon zarra = Coupon.builder()
            .category(Category.FASHION)
            .title("one+one")
            .description("buy one get the second hinam")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(100.0)
            .image("https://imgprd19.hobbylobby.com/9/5f/26/95f264323ae49e65b2a53a909fcd7d9ee659f3c7/350Wx350H-422519-0320.jpg")
            .amount(amountSetter())
            .company(hyundai)
            .companyName(hyundai.getName())
            .build();

    public Coupon sony = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("ps5 from china")
            .description("controller coupon")
            .startDate(Date.valueOf(LocalDate.now().minusDays(1)))
            .endDate(Date.valueOf(LocalDate.now().plusDays(1)))
            .price(0.99)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLYIOJGNjytFuvQBUP26PxKG0_KnozY0VFQw&usqp=CAU")
            .amount(4)
            .company(gucci)
            .companyName(gucci.getName())
            .build();

    public Coupon totto = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("dessert coupon")
            .description("yam yam")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(80.0)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzxhCdbVscGEpuH-kfnApIjJzbJixSypOrA&usqp=CAU")
            .amount(amountSetter())
            .company(hyundai)
            .companyName(hyundai.getName())
            .build();

    public Coupon skiDeal = Coupon.builder()
            .category(Category.VACATION)
            .title("ski trip")
            .description("vacation to France")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(1000.0)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRChpOO-ThsUdFR-Jxn4mQJ25RWZ1XB95PDKg&usqp=CAU")
            .amount(amountSetter())
            .company(hyundai)
            .companyName(hyundai.getName())
            .build();

    public Coupon cinemacity = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("vip lounge cinema-city")
            .description("free food")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(60.0)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRrsx_dmJPFOV4KzPauGN6da9PWjZOp8IhSA&usqp=CAU")
            .amount(amountSetter())
            .company(hyundai)
            .companyName(hyundai.getName())
            .build();

    public Coupon musicPerformanceClub = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("vip concert Maroon5")
            .description("vip accesses ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(125.0)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTRujxIzPN5JJcnt3695PP0_ZWsLVhOAjQEQ&usqp=CAU")
            .amount(amountSetter())
            .company(gucci)
            .companyName(gucci.getName())
            .build();

    public Coupon tatooMania = Coupon.builder()
            .category(Category.FASHION)
            .title("tattoo in discount")
            .description("second tattoo is free ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(210.10)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIDXwLe4LZKZuf8pcmiffWHhM4DCl6r7rYdg&usqp=CAU")
            .amount(amountSetter())
            .company(gucci)
            .companyName(gucci.getName())
            .build();

    public Coupon gymBoxer = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("free month at gym")
            .description("free month membership")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(30.0)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxPShUboZU5q0MQsC0LZSmgp57-J20S8x04w&usqp=CAU")
            .amount(amountSetter())
            .company(ibm)
            .companyName(ibm.getName())
            .build();

    public Coupon hairForYou = Coupon.builder()
            .category(Category.FASHION)
            .title("hair cut")
            .description("free hir cut ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(20.0)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnyYNr4OBk1UD-J2e_CT__EWyQ8XaqtUlREQ&usqp=CAU")
            .amount(amountSetter())
            .company(ibm)
            .companyName(ibm.getName())
            .build();

    public Coupon led = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("30 meter led")
            .description("buy 20 bubble lam in discount")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(340.0)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQPKG4o1LUMafNbgaqY8alXs2tZE8lpi3ngA&usqp=CAU")
            .amount(amountSetter())
            .company(gucci)
            .companyName(gucci.getName())
            .build();

    public Coupon steakHouse = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("couple meal")
            .description("get 3 steaks 2 ribs 5 starters ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(550.0)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPXlhOjW6BH8-5AYxv0WSXbNkEDZO4LhXZVA&usqp=CAU")
            .amount(amountSetter())
            .company(gucci)
            .companyName(gucci.getName())
            .build();

    public Coupon romanticVacation = Coupon.builder()
            .category(Category.VACATION)
            .title("vacation")
            .description(" romantic weakened in the nature ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(1000.0)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSn0__TVpos8NkSdK1ccW7pKQyOz5Tf3mM_vA&usqp=CAU")
            .amount(amountSetter())
            .company(ibm)
            .companyName(ibm.getName())
            .build();

    public Coupon vacationInTheCity = Coupon.builder()
            .category(Category.VACATION)
            .title("hotel in tel aviv")
            .description("weakened in Hilton hotel ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(2000.0)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSzvVbgJNlZi83ZA4INh1LwXfz3lPP2jJFMAw&usqp=CAU")
            .amount(amountSetter())
            .company(facebook)
            .companyName(facebook.getName())
            .build();

    public Coupon shoesMania = Coupon.builder()
            .category(Category.FASHION)
            .title("nike shoes")
            .description("second pair for free")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(100.0)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaUZsWvEYkFYutWkWbDHj9T9hKc1mICAQUNNHM3vRBT0Rj5r-qSX0goEIxjDPv664Xvdo&usqp=CAU")
            .amount(amountSetter())
            .company(facebook)
            .companyName(facebook.getName())
            .build();

    public Coupon electra = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("mini air condition")
            .description("air condition for small spaces")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(180.0)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUY48kSMJC0m-WVJF8RfgUyRlUIOCF0rgKvQ&usqp=CAU")
            .amount(amountSetter())
            .company(ibm)
            .companyName(ibm.getName())
            .build();

    public Coupon jimbori = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("Jimbory")
            .description("5 entries to the jumbori")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(140)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQG247Q5Di7BeaevqkHpeM7GBzJGq_bjtV1wg&usqp=CAU")
            .amount(amountSetter())
            .company(facebook)
            .companyName(facebook.getName())
            .build();

    public Coupon weRun = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title(" tennis lessons ")
            .description("5 tennis lessons")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(448)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzTCY9WzSMRs9uX0WLULa_3xz4ZKs4JkSrkA&usqp=CAU")
            .amount(amountSetter())
            .company(google)
            .companyName(google.getName())
            .build();

    public Coupon luxuryWatches = Coupon.builder()
            .category(Category.FASHION)
            .title(" Rollex ")
            .description("Rollex db321")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(32122)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9tuufHDl9Dg9sJaJBAN6Zbhi5ciM9ZeFjgA&usqp=CAU")
            .amount(amountSetter())
            .company(google)
            .companyName(google.getName())
            .build();

    public Coupon tripAdvice = Coupon.builder()
            .category(Category.VACATION)
            .title(" into the wild ")
            .description("3 days trip in the desert ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(1456)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVcIGHinGf3Tq2TsgbQns8Sf5W5HTjhTZMnQ&usqp=CAU")
            .amount(amountSetter())
            .company(google)
            .companyName(google.getName())
            .build();

    public Coupon chocolateFactory = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("chocolate adventure")
            .description("tour on chocolate factory")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(118)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQp5wpyTC2P4rypHbCaw4QZx71h_2g__0Eb4A&usqp=CAU")
            .amount(amountSetter())
            .company(ibm)
            .companyName(ibm.getName())
            .build();

    public Coupon wineAndFINE = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("wine fest")
            .description(" drink all day ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(90)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0-WtN5D-4LFA-RygNc04f5SvSF_K0Ge3Nqw&usqp=CAU")
            .amount(amountSetter())
            .company(facebook)
            .companyName(facebook.getName())
            .build();

    public Coupon noaORmergi = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("noa or mergi")
            .description("tickets for Noa Kirel or Mergi concert")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(510)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYjA6x5DAu6WBf988PIY6aQW80RkpipJVw_A&usqp=CAU")
            .amount(amountSetter())
            .company(google)
            .companyName(google.getName())
            .build();

    public Coupon socks4U = Coupon.builder()
            .category(Category.FASHION)
            .title("socks for you")
            .description("5 pairs of socks in different colors")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(130)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQEC0rfEbrho1kFztryNJyqgowsI9sQMrOqnw&usqp=CAU")
            .amount(amountSetter())
            .company(google)
            .companyName(google.getName())
            .build();

    public Coupon noSecond = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("eyal ein sheni lo")
            .description("food for a cute couple")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(1200)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8PAGU_MxNXvFapQTU7z0lJlXYJ7ZClKj2pw&usqp=CAU")
            .amount(amountSetter())
            .company(google)
            .companyName(google.getName())
            .build();

    public Coupon expiredCoupon = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("mcdonald's")
            .description("one meal")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(1)))
            .price(0.99)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnZsdhkKFgnRq6LqF3bjG2H8zBKUT0l6Bg_w&usqp=CAU")
            .amount(amountSetter())
            .company(google)
            .companyName(google.getName())
            .build();

    public Coupon noAmountCoupon = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("macBook Air m1")
            .description("Apple original")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(1)))
            .price(99)
            .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyQjtyTD2KxAaPC9duuIng6X--zdNTiofMnw&usqp=CAU")
            .amount(0)
            .company(google)
            .companyName(google.getName())
            .build();

    ///customers///

    public Customer itzik = Customer.builder()
            .firstName("itzik")
            .lastName("seaman-tof")
            .email("itzik@gmail.com")
            .password("1234")
            .build();
    public Customer tomer = Customer.builder()
            .firstName("tomer")
            .lastName("shimony")
            .email("tomer@gmail.com")
            .password("1234")
            .build();
    public Customer yuri = Customer.builder()
            .firstName("yuri")
            .lastName("roeeeei")
            .email("yuri@gmail.com")
            .password("1234")
            .build();
    public Customer barak = Customer.builder()
            .firstName("barak")
            .lastName("hamdani")
            .email("barak@gmail.com")
            .password("1234")
            .build();
    public Customer asi = Customer.builder()
            .firstName("asi")
            .lastName("taragano")
            .email("asi@gmail.com")
            .password("1234")
            .build();
    public Customer zeevNoCoupons = Customer.builder()
            .firstName("zeevNoCoupons")
            .lastName("mindali")
            .email("zeev@gmail.com")
            .password("zeev")
            .build();

    @Override
    public void run(String... args) throws Exception {
        adminService.addCompany(hyundai);
        adminService.addCompany(gucci);
        adminService.addCompany(ibm);
        adminService.addCompany(facebook);
        adminService.addCompany(google);

        adminService.addCustomer(itzik);
        adminService.addCustomer(tomer);
        adminService.addCustomer(yuri);
        adminService.addCustomer(barak);
        adminService.addCustomer(asi);
        adminService.addCustomer(zeevNoCoupons);

        companyService.addCoupon(tvHouse);
        companyService.addCoupon(zarra);
        companyService.addCoupon(totto);
        companyService.addCoupon(skiDeal);
        companyService.addCoupon(cinemacity);
        companyService.addCoupon(sony);
        companyService.addCoupon(musicPerformanceClub);
        companyService.addCoupon(tatooMania);
        companyService.addCoupon(gymBoxer);
        companyService.addCoupon(hairForYou);
        companyService.addCoupon(led);
        companyService.addCoupon(steakHouse);
        companyService.addCoupon(romanticVacation);
        companyService.addCoupon(vacationInTheCity);
        companyService.addCoupon(shoesMania);
        companyService.addCoupon(electra);
        companyService.addCoupon(jimbori);
        companyService.addCoupon(weRun);
        companyService.addCoupon(luxuryWatches);
        companyService.addCoupon(tripAdvice);
        companyService.addCoupon(chocolateFactory);
        companyService.addCoupon(wineAndFINE);
        companyService.addCoupon(noaORmergi);
        companyService.addCoupon(socks4U);
        companyService.addCoupon(noSecond);

        companyService.addCoupon(expiredCoupon);
        this.expiredCoupon.setEndDate(Date.valueOf(LocalDate.now().minusYears(1)));
        this.expiredCoupon.setPurchases(new HashSet<>());
        companyService.updateCoupon(expiredCoupon);

        companyService.addCoupon(noAmountCoupon);

        customerService.purchaseCoupon(adminService.getOneCustomer(1),1);
        customerService.purchaseCoupon(adminService.getOneCustomer(1),2);
        customerService.purchaseCoupon(adminService.getOneCustomer(1),3);
        customerService.purchaseCoupon(adminService.getOneCustomer(1),4);
        customerService.purchaseCoupon(adminService.getOneCustomer(1),5);
        customerService.purchaseCoupon(adminService.getOneCustomer(2),6);
        customerService.purchaseCoupon(adminService.getOneCustomer(2),7);
        customerService.purchaseCoupon(adminService.getOneCustomer(2),8);
        customerService.purchaseCoupon(adminService.getOneCustomer(2),9);
        customerService.purchaseCoupon(adminService.getOneCustomer(2),10);
        customerService.purchaseCoupon(adminService.getOneCustomer(3),11);
        customerService.purchaseCoupon(adminService.getOneCustomer(3),12);
        customerService.purchaseCoupon(adminService.getOneCustomer(3),13);
        customerService.purchaseCoupon(adminService.getOneCustomer(3),14);
        customerService.purchaseCoupon(adminService.getOneCustomer(3),15);
        customerService.purchaseCoupon(adminService.getOneCustomer(4),16);
        customerService.purchaseCoupon(adminService.getOneCustomer(4),17);
        customerService.purchaseCoupon(adminService.getOneCustomer(4),18);
        customerService.purchaseCoupon(adminService.getOneCustomer(4),19);
        customerService.purchaseCoupon(adminService.getOneCustomer(4),20);
        customerService.purchaseCoupon(adminService.getOneCustomer(5),21);
        customerService.purchaseCoupon(adminService.getOneCustomer(5),22);
        customerService.purchaseCoupon(adminService.getOneCustomer(5),23);
        customerService.purchaseCoupon(adminService.getOneCustomer(5),24);
        customerService.purchaseCoupon(adminService.getOneCustomer(5),25);
    }
}
