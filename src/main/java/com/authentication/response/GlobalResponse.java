package com.authentication.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class GlobalResponse<T> {
    private T data;
    private HttpStatus status;
    private Integer statusCode;
}
