package com.ToDo_App.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "todo")
@Data
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID toDoId;

    @NotNull(message = "Title cannot be null")
    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
    private String title;

    @Size(max = 2048, message = "Description must be less than 2048 characters")
    private String description;

    private boolean completed;

    @NotNull(message = "Priority cannot be null")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDateTime dueDate;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public enum Priority {
        LOW, MEDIUM, HIGH
    }
}