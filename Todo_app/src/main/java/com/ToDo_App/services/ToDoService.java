package com.ToDo_App.services;

import com.ToDo_App.data.models.User;
import com.ToDo_App.dto.todo.ToDoDto;
import com.ToDo_App.dto.todo.request.ToDoCreateOrUpdateRequestDto;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.UUID;

public interface ToDoService {
    List<ToDoDto> fetchAllToDos(User user);

    ToDoDto fetchToDoById(User user, UUID todoId);

    ToDoDto createToDo(ToDoCreateOrUpdateRequestDto toDoDto);

    ToDoDto updateToDo(UUID todoId, ToDoCreateOrUpdateRequestDto toDoDto, HttpSession httpSession);

    boolean deleteToDo(UUID todoId, HttpSession httpSession);

    ToDoDto markAsCompleted(UUID todoId, HttpSession httpSession);

    ToDoDto markAsIncomplete(UUID todoId, HttpSession httpSession);

    List<ToDoDto> searchToDos(String searchTerm, boolean completed, HttpSession httpSession);
}
