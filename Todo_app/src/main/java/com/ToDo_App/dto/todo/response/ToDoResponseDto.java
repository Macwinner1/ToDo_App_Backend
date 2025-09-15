package com.ToDo_App.dto.todo.response;

import com.ToDo_App.dto.BaseResponseDto;
import com.ToDo_App.dto.todo.ToDoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;


@EqualsAndHashCode(callSuper=true)
@Data
@AllArgsConstructor @NoArgsConstructor
public class ToDoResponseDto extends BaseResponseDto {
    private ToDoDto data;
    public ToDoResponseDto(HttpStatusCode statusCode, String statusMessage, ToDoDto data) {
        super(statusCode, statusMessage);
        this.data = data;
    }
}
