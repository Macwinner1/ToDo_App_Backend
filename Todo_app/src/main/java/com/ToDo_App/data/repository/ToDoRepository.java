package com.ToDo_App.data.repository;

import com.ToDo_App.data.models.ToDo;
import com.ToDo_App.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToDoRepository extends JpaRepository<ToDo, Integer> {
    @Query("SELECT t from todo t where t.user = :user")
    List<ToDo> getAllToDosFromUser(@Param("com/todoApp/dto/user") User user);
    @Query("SELECT t from todo t where t.user = :user AND t.todoId = :todoId")
    Optional<ToDo> getToDoById(@Param("com/todoApp/dto/user") User user, @Param("todoId") UUID todoId);

    @Query("SELECT t FROM todo t WHERE t.user = :user AND " +
            "((t.isCompleted = :completed) OR (LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(t.memo) LIKE LOWER(CONCAT('%', :searchTerm, '%'))))")
    List<ToDo> searchTodos(
            @Param("com/todoApp/dto/user") User user,
            @Param("searchTerm") String searchTerm,
            @Param("completed") boolean completed);
}
