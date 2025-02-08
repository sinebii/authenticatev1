package com.authentication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String sayHello(){
        return "POST Hello World";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/get")
    public String getHello(){
        return "Get request";
    }
}
