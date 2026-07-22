package com.dhaneshlakhwani.rideshare.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/user/dashboard")
    public String dashboard() { return "user/dashboard"; }
}
