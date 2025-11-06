package com.ToDo_App.data.repository;


import com.ToDo_App.data.models.ToDo;
import com.ToDo_App.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ToDoRepository extends JpaRepository<ToDo, UUID> {
    List<ToDo> findByUser(User user);

    @Query("SELECT t FROM ToDo t WHERE t.user = :user AND (:completed IS NULL OR t.completed = :completed) AND (:keyword IS NULL OR t.title LIKE %:keyword%)")
    List<ToDo> searchTodos(User user, Boolean completed, String keyword);

    long countByUser(User user);
    long countByUserAndCompleted(User user, boolean completed);
    long countByUserAndPriority(User user, ToDo.Priority priority);
}