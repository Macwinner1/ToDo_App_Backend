package com.ToDo_App.dto.todo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ToDoDto {
    private UUID todoId;

    private String title;
    private String memo;
    private boolean isCompleted;
    private boolean isImportant;
    private LocalDateTime completedAt;
}
