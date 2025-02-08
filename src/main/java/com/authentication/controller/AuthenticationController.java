package com.authentication.controller;

import com.authentication.payload.LoginPayload;
import com.authentication.payload.UserPayload;
import com.authentication.response.GlobalResponse;
import com.authentication.response.LoginResponse;
import com.authentication.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserPayload userPayload, HttpServletRequest request){
        GlobalResponse response = authenticationService.register(userPayload, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginPayload loginPayload){
        LoginResponse response = authenticationService.login(loginPayload);
        return ResponseEntity.ok(response);
    }
}
