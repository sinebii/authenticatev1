package com.authentication.controller;

import com.authentication.payload.CreateTaskDto;
import com.authentication.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskDto createTaskDto){
        return new ResponseEntity<>(todoService.createTask(createTaskDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/get")
    public String getHello(){
        return "Get request";
    }
}
