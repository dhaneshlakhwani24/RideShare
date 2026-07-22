package com.dhaneshlakhwani.rideshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RideShareBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RideShareBookingApplication.class, args);
    }
}
