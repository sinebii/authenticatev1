package com.authentication.response;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class LoginResponse<T>{
    private T user;
    private HttpStatus status;
    private Integer statusCode;
    private String token;
    private String message;

}
