package com.nitelife.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping("/")
    public String redirectToIndex() {
        return "index";
    }
}