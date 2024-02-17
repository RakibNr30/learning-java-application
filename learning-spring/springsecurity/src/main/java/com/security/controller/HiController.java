package com.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @GetMapping("/hi")
    public String sayHi() {
        return "Hi all!";
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello all!";
    }
}
