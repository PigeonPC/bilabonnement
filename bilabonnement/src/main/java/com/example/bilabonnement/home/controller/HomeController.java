package com.example.bilabonnement.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return"home/index";
    }

    @PostMapping("/")
    public String handlePost() {
        //noget med at håndtere formmular eller lign.
        return "home/index"; //eller noget...
    }
}
