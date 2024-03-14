package com.sungchef.sungchef.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping(value= "/test")
    public String testController () {
        return "Hello World";
    }
}
