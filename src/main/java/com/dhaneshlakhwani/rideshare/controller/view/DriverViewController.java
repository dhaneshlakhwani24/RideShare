package com.dhaneshlakhwani.rideshare.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DriverViewController {
    @GetMapping("/driver/dashboard")
    public String dashboard() { return "driver/dashboard"; }
}
