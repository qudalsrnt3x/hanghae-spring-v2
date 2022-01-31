package com.hanghae.hanghaespringv2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping({"/", ""})
    public String main() {
        return "index";
    }

}
