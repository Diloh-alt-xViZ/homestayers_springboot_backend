package com.developer.homestayersbackend.controller;

import com.developer.homestayersbackend.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello World";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/checkId")
    public String checkId(@AuthenticationPrincipal User user) {
        Long userId = user.getId();
        return userId.toString();
    }
}
