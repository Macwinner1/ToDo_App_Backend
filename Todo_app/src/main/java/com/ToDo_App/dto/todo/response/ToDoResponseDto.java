package com.ToDo_App.dto.todo.response;

import com.ToDo_App.dto.BaseResponseDto;
import com.ToDo_App.dto.todo.ToDoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ToDoResponseDto extends BaseResponseDto {
    public ToDoResponseDto(HttpStatus status, String message, ToDoDto data) {
        super(status, message, data);
    }
}