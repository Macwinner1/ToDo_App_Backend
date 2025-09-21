package com.ToDo_App.data.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "Todo")
public class ToDo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID todoId;

    private String title;
    @Column(name = "memo", length = 2048)
    private String memo;
    private boolean isCompleted;
    private boolean isImportant;
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
