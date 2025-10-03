package com.ToDo_App.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "Todo")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID todoId;

    private String title;
    @Column(name = "description", length = 2048)
    private String description;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @Column(name = "priority")
    private String priority;
    private String dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}

