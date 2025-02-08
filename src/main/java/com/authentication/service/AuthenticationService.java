package com.authentication.service;

import com.authentication.model.User;
import com.authentication.payload.LoginPayload;
import com.authentication.payload.UserPayload;
import com.authentication.response.GlobalResponse;
import com.authentication.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    GlobalResponse register(UserPayload userPayload, HttpServletRequest request);
    LoginResponse login (LoginPayload loginPayload);
    User findUser(Long id);
}
