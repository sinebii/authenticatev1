package com.authentication.service.impl;

import com.authentication.exception.UserNotFoundException;
import com.authentication.model.Todo;
import com.authentication.model.User;
import com.authentication.payload.CreateTaskDto;
import com.authentication.repository.TodoRepository;
import com.authentication.repository.UserRepository;
import com.authentication.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    @Override
    public CreateTaskDto createTask(CreateTaskDto createTaskDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found"));
        Todo todo = Todo.builder()
                .title(createTaskDto.getTitle())
                .description(createTaskDto.getDescription())
                .completed(false)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
        Todo savedTodo = todoRepository.save(todo);
        return Stream.of(savedTodo)
                .map(t -> new CreateTaskDto(
                        t.getId(),
                        t.getTitle(),
                        t.getDescription(),
                        t.isCompleted(),
                        t.getCreatedAt()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to map Todo to DTO"));
    }
}
