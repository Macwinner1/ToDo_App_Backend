package com.ToDo_App.dto.todo.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ToDoCreateOrUpdateRequestDto {
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 2, max = 255, message = "The length of the title should be between 2 and 255")
    private String title;

    @Size(max = 2048, message = "The length of the description should be less than 2048")
    private String description;

    private boolean completed;

    @NotEmpty(message = "Priority cannot be empty")
    @Pattern(regexp = "LOW|MEDIUM|HIGH", message = "Priority must be LOW, MEDIUM, or HIGH")
    private String priority;

    private String dueDate;
}