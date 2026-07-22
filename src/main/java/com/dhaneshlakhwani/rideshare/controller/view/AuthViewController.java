package com.dhaneshlakhwani.rideshare.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {
    @GetMapping("/login")
    public String loginPage() { return "auth/login"; }

    @GetMapping("/register/user")
    public String registerUserPage() { return "auth/register-user"; }

    @GetMapping("/register/driver")
    public String registerDriverPage() { return "auth/register-driver"; }
}
