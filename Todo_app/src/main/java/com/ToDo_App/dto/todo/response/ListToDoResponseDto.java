package com.ToDo_App.dto.todo.response;

import com.ToDo_App.dto.BaseResponseDto;
import com.ToDo_App.dto.todo.ToDoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListToDoResponseDto extends BaseResponseDto {
    public ListToDoResponseDto(HttpStatus status, String message, List<ToDoDto> data) {
        super(status, message, data);
    }
}