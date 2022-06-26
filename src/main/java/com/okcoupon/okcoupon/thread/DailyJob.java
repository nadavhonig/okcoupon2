package com.okcoupon.okcoupon.thread;

import com.okcoupon.okcoupon.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@EnableAsync
@EnableScheduling
public class DailyJob {

    @Autowired
    AdminService adminService;

    /**
     * Async Method that runs once a day at 00:00:00, it deletes all the expired coupons in the system
     */
    @Async
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Jerusalem")
    @Transactional
    public void eraseCoupon() {
        adminService.deleteExpiredCoupon();
    }
}

