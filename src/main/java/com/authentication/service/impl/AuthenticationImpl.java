package com.authentication.service.impl;

import com.authentication.events.RegistrationCompleteEvent;
import com.authentication.exception.RoleNotFoundException;
import com.authentication.exception.UserNotFoundException;
import com.authentication.model.Role;
import com.authentication.model.User;
import com.authentication.payload.LoginPayload;
import com.authentication.payload.UserPayload;
import com.authentication.repository.RoleRepository;
import com.authentication.repository.UserRepository;
import com.authentication.response.GlobalResponse;
import com.authentication.response.LoginResponse;
import com.authentication.security.JwtTokenProvider;
import com.authentication.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ApplicationEventPublisher publisher;

    @Override
    public GlobalResponse register(UserPayload userPayload,HttpServletRequest request) {
        Boolean existByEmail = userRepository.existsByEmail(userPayload.getEmail());
        Boolean existByUsername = userRepository.existsByUsername(userPayload.getUsername());
        if(existByEmail){
            return GlobalResponse.builder()
                    .data("Email Exist")
                    .status(HttpStatus.CONFLICT)
                    .statusCode(HttpStatus.CONFLICT.value())
                    .build();
        }else if(existByUsername){
            return GlobalResponse.builder()
                    .data("Username already Exist")
                    .status(HttpStatus.CONFLICT)
                    .statusCode(HttpStatus.CONFLICT.value())
                    .build();
        }else{
            User user = new User();
            user.setFirstName(userPayload.getFirstName());
            user.setLastName(userPayload.getLastName());
            user.setUsername(userPayload.getUsername());
            user.setMiddleName(userPayload.getMiddleName());
            user.setEmail(userPayload.getEmail());
            user.setPassword(passwordEncoder.encode(userPayload.getPassword()));
            user.setGender(userPayload.getGender());
            user.setState(userPayload.getState());
            user.setLga(userPayload.getLga());
            user.setCity(userPayload.getCity());
            user.setAddress(userPayload.getAddress());
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findRoleById(userPayload.getRoleId()).orElseThrow(()->new RoleNotFoundException(HttpStatus.BAD_REQUEST,"Role not found"));
            roles.add(role);
            user.setRoles(roles);
            User saved = userRepository.save(user);
            publisher.publishEvent(new RegistrationCompleteEvent(saved, "http://67.217.58.16:7070"));
            return GlobalResponse.builder()
                    .data(userPayload)
                    .status(HttpStatus.CREATED)
                    .statusCode(HttpStatus.CREATED.value())
                    .build();
        }

    }

    @Override
    public LoginResponse login(LoginPayload loginPayload) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginPayload.getUsernameOrEmail(),
                    loginPayload.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<User> user = userRepository.findByUsername(authentication.getName());
            user.get().setPassword(null);
            LoginResponse response = LoginResponse.builder()
                    .message("Logged in Successfully")
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .token(jwtTokenProvider.generateToken(authentication))
                    .user(user.get())
                    .build();
            return response;
        } catch (BadCredentialsException ex) {
            // Handle incorrect password scenario
            return LoginResponse.builder()
                    .message("Invalid username or password.")
                    .status(HttpStatus.UNAUTHORIZED)
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .build();
        } catch (AuthenticationException ex) {
            // Handle other authentication failures
            return LoginResponse.builder()
                    .message("Authentication failed: " + ex.getMessage())
                    .status(HttpStatus.UNAUTHORIZED)
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }
    }

    @Override
    public User findUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found"));
        return null;
    }


}
