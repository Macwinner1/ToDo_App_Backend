package com.ToDo_App.controllers;


import com.ToDo_App.data.models.User;
import com.ToDo_App.dto.BaseResponseDto;
import com.ToDo_App.dto.todo.ToDoDto;
import com.ToDo_App.dto.todo.request.ToDoCreateOrUpdateRequestDto;
import com.ToDo_App.dto.todo.response.ListToDoResponseDto;
import com.ToDo_App.dto.todo.response.ToDoResponseDto;
import com.ToDo_App.services.ToDoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ToDoController {

    private final ToDoService toDoService;

    @Autowired
    public  ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }


    @GetMapping("/")
    public ResponseEntity<?> fetchAllToDos(HttpSession session) {
        User user = (User) session.getAttribute("username");
        if(user == null){
            return ResponseEntity.status(401).body("Not Logged in");
        }
        List<ToDoDto> toDos = toDoService.fetchAllToDos(user);
        return ResponseEntity.status(
                HttpStatus.OK
        ).body(
                new ListToDoResponseDto(
                        HttpStatus.OK,
                        "All Todos fetched successfully",
                        toDos
                )
        );
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<ToDoResponseDto> fetchTodoById(@PathVariable UUID todoId, User user) {
        ToDoDto toDo = toDoService.fetchToDoById(user, todoId);
        return ResponseEntity.status(
                HttpStatus.OK
        ).body(
                new ToDoResponseDto(
                        HttpStatus.OK,
                        "ToDo Fetched Successfully",
                        toDo
                )
        );
    }

    @PostMapping("/todo/create")
    public ResponseEntity<ToDoResponseDto> createToDo(@Valid @RequestBody ToDoCreateOrUpdateRequestDto toDoCreateOrUpdateRequestDto) {
        ToDoDto todo = toDoService.createToDo(toDoCreateOrUpdateRequestDto);
        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(
                new ToDoResponseDto(
                        HttpStatus.CREATED,
                        "ToDo Created Successfully",
                        todo
                )
        );
    }

    @PutMapping("/{toDoId}")
    public ResponseEntity<ToDoResponseDto> updateToDo(@PathVariable UUID toDoId, @Valid @RequestBody UUID todoId, ToDoCreateOrUpdateRequestDto toDoDto, HttpSession httpSession) {
        ToDoDto toDo = toDoService.updateToDo(todoId, toDoDto, httpSession);
        return ResponseEntity.status(
                HttpStatus.OK
        ).body(
                new ToDoResponseDto(
                        HttpStatus.OK,
                        "ToDo Updated Successfully",
                        toDo
                )
        );
    }

    @DeleteMapping("/{toDoId}")
    public ResponseEntity<BaseResponseDto> deleteToDo(@PathVariable UUID toDoId, HttpSession httpSession){
        boolean isDeleted = toDoService.deleteToDo(toDoId, httpSession);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new BaseResponseDto(
                            HttpStatus.NO_CONTENT,
                            "ToDo Deleted Successfully"
                    )
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new BaseResponseDto(
                        HttpStatus.BAD_REQUEST,
                        "ToDo Deleted Unsuccessfully."
                )
        );
    }

    @PatchMapping("/{toDoId}/complete")
    public ResponseEntity<ToDoResponseDto> markAsCompleted(@PathVariable UUID toDoId, HttpSession httpSession){
        ToDoDto toDo = toDoService.markAsCompleted(toDoId, httpSession);
        return ResponseEntity.ok(new ToDoResponseDto(HttpStatus.OK, "ToDo marked as completed", toDo));
    }

    @PatchMapping("/{toDoId}/incomplete")
    public ResponseEntity<ToDoResponseDto> markAsIncomplete(@PathVariable UUID toDoId, HttpSession httpSession){
        ToDoDto toDo = toDoService.markAsIncomplete(toDoId, httpSession);
        return ResponseEntity.ok(new ToDoResponseDto(HttpStatus.OK, "ToDo marked as incomplete", toDo));
    }

    @GetMapping("/search")
    public ResponseEntity<ListToDoResponseDto> searchTodos(
            @RequestParam(required = false,defaultValue = "") String searchTerm,
            @RequestParam(required = false,defaultValue = "false") Boolean completed,
            HttpSession httpSession
    ){
                List<ToDoDto> todos = toDoService.searchToDos(searchTerm, completed, httpSession);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ListToDoResponseDto(HttpStatus.OK, "ToDos retrieved", todos)
                );
    }

}
