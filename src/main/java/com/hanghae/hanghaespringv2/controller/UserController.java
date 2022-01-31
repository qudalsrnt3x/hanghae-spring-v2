package com.hanghae.hanghaespringv2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/auth/signup")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/login")
    public String loginForm() {
        return "user/loginForm";
    }

}
