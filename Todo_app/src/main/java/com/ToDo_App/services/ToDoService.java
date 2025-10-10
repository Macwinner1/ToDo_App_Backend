package com.ToDo_App.services;

import com.ToDo_App.dto.todo.request.ToDoCreateOrUpdateRequestDto;
import com.ToDo_App.dto.todo.response.ListToDoResponseDto;
import com.ToDo_App.dto.todo.response.StatsResponseDto;
import com.ToDo_App.dto.todo.response.ToDoResponseDto;
import jakarta.servlet.http.HttpSession;

public interface ToDoService {
    ToDoResponseDto createToDo(ToDoCreateOrUpdateRequestDto toDoCreateOrUpdateRequestDto, HttpSession session);
    ToDoResponseDto updateToDo(String toDoId, ToDoCreateOrUpdateRequestDto toDoCreateOrUpdateRequestDto, HttpSession session);
    ToDoResponseDto fetchToDoById(String toDoId, HttpSession session);
    ListToDoResponseDto fetchAllToDos(HttpSession session);
    ListToDoResponseDto searchTodos(Boolean completed, String keyword, HttpSession session);
    ToDoResponseDto completeToDo(String toDoId, HttpSession session);
    ToDoResponseDto incompleteToDo(String toDoId, HttpSession session);
    void deleteToDo(String toDoId, HttpSession session);
    StatsResponseDto getTodoStats(HttpSession session);
}