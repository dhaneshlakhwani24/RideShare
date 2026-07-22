package com.dhaneshlakhwani.rideshare.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {
    @GetMapping("/admin/dashboard")
    public String dashboard() { return "admin/dashboard"; }
}
