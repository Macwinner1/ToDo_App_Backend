package com.ToDo_App.utils.mapper;

import com.ToDo_App.data.models.ToDo;
import com.ToDo_App.dto.todo.ToDoDto;
import com.ToDo_App.dto.todo.request.ToDoCreateOrUpdateRequestDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ToDoMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ToDoDto toToDoDto(ToDo toDo) {
        ToDoDto toDoDto = new ToDoDto();
        toDoDto.setTodoId(toDo.getToDoId());
        toDoDto.setTitle(toDo.getTitle());
        toDoDto.setDescription(toDo.getDescription());
        toDoDto.setCompleted(toDo.isCompleted());
        toDoDto.setPriority(toDo.getPriority().name());
        toDoDto.setDueDate(toDo.getDueDate());
        toDoDto.setCreatedAt(toDo.getCreatedAt());
        return toDoDto;
    }

    public ToDo toToDo(ToDoCreateOrUpdateRequestDto requestDto) {
        ToDo toDo = new ToDo();
        toDo.setTitle(requestDto.getTitle());
        toDo.setDescription(requestDto.getDescription());
        toDo.setCompleted(requestDto.isCompleted());
        toDo.setPriority(ToDo.Priority.valueOf(requestDto.getPriority()));
        if (requestDto.getDueDate() != null) {
            toDo.setDueDate(LocalDateTime.parse(requestDto.getDueDate(), FORMATTER));
        }
        toDo.setCreatedAt(LocalDateTime.now());
        return toDo;
    }
}