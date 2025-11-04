package com.ToDo_App.dto.user;

import com.ToDo_App.dto.BaseResponseDto;
import com.ToDo_App.dto.todo.ToDoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID userId;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private List<ToDoDto> todos;

    public void setToDos(List<ToDoDto> todos) {
        this.todos = todos;
    }
}