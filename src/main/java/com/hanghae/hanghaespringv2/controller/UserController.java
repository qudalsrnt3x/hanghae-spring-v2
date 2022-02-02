package com.hanghae.hanghaespringv2.controller;

import com.hanghae.hanghaespringv2.config.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
