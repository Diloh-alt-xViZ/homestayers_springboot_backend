package com.developer.homestayersbackend.controller;


import com.developer.homestayersbackend.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/demo")
public class DemoController {



    @GetMapping
    public ResponseEntity<String> get(@AuthenticationPrincipal User user) {

        String email = user.getEmail();

        return new ResponseEntity<>("Hello World:"+email, HttpStatus.OK);
    }

    @GetMapping("/demo1")
    public ResponseEntity<String> getDemo() {
        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        return ResponseEntity.ok("Hello  from secure endpoint");
    }

    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard() {
        return ResponseEntity.ok("Hello  from Dashboard");
    }
}
