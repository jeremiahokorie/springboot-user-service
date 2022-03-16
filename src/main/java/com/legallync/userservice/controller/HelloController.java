package com.legallync.userservice.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Legallync API Documentation")
public class HelloController {

    @GetMapping("/hello")
    public String helloController(){
        return "Hello welcome to legallyc";
    }
}
