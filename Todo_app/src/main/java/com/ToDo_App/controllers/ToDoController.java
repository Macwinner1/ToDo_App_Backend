package com.ToDo_App.controllers;

import com.ToDo_App.dto.BaseResponseDto;
import com.ToDo_App.dto.todo.request.ToDoCreateOrUpdateRequestDto;
import com.ToDo_App.dto.todo.response.ListToDoResponseDto;
import com.ToDo_App.dto.todo.response.StatsResponseDto;
import com.ToDo_App.dto.todo.response.ToDoResponseDto;
import com.ToDo_App.services.ToDoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(originPatterns = "http://localhost:*", allowCredentials = "true")
public class ToDoController {
    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping("/create")
    public ToDoResponseDto createToDo(@Valid @RequestBody ToDoCreateOrUpdateRequestDto toDoCreateOrUpdateRequestDto, HttpSession session) {
        return toDoService.createToDo(toDoCreateOrUpdateRequestDto, session);
    }

    @PutMapping("/update/{todoId}")
    public ToDoResponseDto updateToDo(@PathVariable String todoId, @Valid @RequestBody ToDoCreateOrUpdateRequestDto toDoCreateOrUpdateRequestDto, HttpSession session) {
        return toDoService.updateToDo(todoId, toDoCreateOrUpdateRequestDto, session);
    }

    @GetMapping("/{todoId}")
    public ToDoResponseDto getToDoById(@PathVariable String todoId, HttpSession session) {
        return toDoService.fetchToDoById(todoId, session);
    }

    @GetMapping("/todos")
    public ListToDoResponseDto getAllToDos(HttpSession session) {
        return toDoService.fetchAllToDos(session);
    }

    @GetMapping("/search")
    public ListToDoResponseDto searchTodos(@RequestParam(required = false) Boolean completed, @RequestParam(required = false) String keyword, HttpSession session) {
        return toDoService.searchTodos(completed, keyword, session);
    }

    @PatchMapping("/{todoId}/complete")
    public ToDoResponseDto completeToDo(@PathVariable String todoId, HttpSession session) {
        return toDoService.completeToDo(todoId, session);
    }

    @PatchMapping("/{todoId}/incomplete")
    public ToDoResponseDto incompleteToDo(@PathVariable String todoId, HttpSession session) {
        return toDoService.incompleteToDo(todoId, session);
    }

    @DeleteMapping("/delete/{todoId}")
    public BaseResponseDto deleteToDo(@PathVariable String todoId, HttpSession session) {
        toDoService.deleteToDo(todoId, session);
        return new BaseResponseDto(HttpStatus.OK, "ToDo deleted successfully", null);
    }

    @GetMapping("/stats")
    public StatsResponseDto getTodoStats(HttpSession session) {
        return toDoService.getTodoStats(session);
    }
}