package com.ToDo_App.dto.todo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ToDoDto {
    private UUID todoId;
    private String title;
    private String description;
    private boolean completed;
    private String priority;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
}