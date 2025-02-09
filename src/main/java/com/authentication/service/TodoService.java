package com.authentication.service;

import com.authentication.payload.CreateTaskDto;

public interface TodoService {
    CreateTaskDto createTask(CreateTaskDto createTaskDto);
}
