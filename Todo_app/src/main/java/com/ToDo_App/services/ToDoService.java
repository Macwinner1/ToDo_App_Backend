package com.ToDo_App.services;

import com.ToDo_App.dto.todo.ToDoDto;
import com.ToDo_App.dto.todo.request.ToDoCreateOrUpdateRequestDto;

import java.util.List;
import java.util.UUID;

public interface ToDoService {
    List<ToDoDto> fetchAllToDos();
    ToDoDto fetchToDoById(UUID todoId);
    ToDoDto createToDo(ToDoCreateOrUpdateRequestDto toDoDto);
    ToDoDto updateToDo(UUID todoId, ToDoCreateOrUpdateRequestDto toDoDto);
    boolean deleteToDo(UUID todoId);
    ToDoDto markAsComplete(UUID todoId);

    ToDoDto markAsCompleted(UUID todoId);

    ToDoDto markAsIncomplete(UUID todoId);

    List<ToDoDto> searchToDos(String searchTerm, boolean completed);
}
