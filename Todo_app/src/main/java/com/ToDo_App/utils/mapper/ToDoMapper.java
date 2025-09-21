package com.ToDo_App.utils.mapper;

import com.ToDo_App.data.models.ToDo;
import com.ToDo_App.dto.todo.ToDoDto;
import com.ToDo_App.dto.todo.request.ToDoCreateOrUpdateRequestDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring")
@Component
public class ToDoMapper {

    public ToDoDto toToDoDto(ToDo toDo){
        ToDoDto  toDoDto = new ToDoDto();
        toDoDto.setTitle(toDo.getTitle());
        toDoDto.setTodoId(toDo.getTodoId());
        toDoDto.setMemo(toDo.getMemo());
        toDoDto.setCompleted(toDo.isCompleted());
        toDoDto.setImportant(toDo.isImportant());
        toDoDto.setCompletedAt(toDo.getCompletedAt());

        return toDoDto;

    }

    public static ToDo mapFromToDoDtoToToDo(
            ToDoDto toDoDto
    ){
        ToDo toDo = new ToDo();
        toDo.setTitle(toDoDto.getTitle());
        toDo.setMemo(toDoDto.getMemo());
        toDo.setTodoId(toDoDto.getTodoId());
        toDo.setCompletedAt(toDoDto.getCompletedAt());

        return toDo;
    }


    public static ToDo fromToDoCreateOrUpdateDto(ToDoCreateOrUpdateRequestDto request){
        ToDo toDo = new ToDo();
        toDo.setTitle(request.getTitle());
        toDo.setMemo(request.getMemo());
        toDo.setCompleted(false);
        toDo.setImportant(request.isImportant());

        return toDo;
    }
    public static ToDo fromToDoCreateOrUpdateRequestDto(ToDoCreateOrUpdateRequestDto toDoDto, ToDo toDo){
        toDo.setTitle(toDoDto.getTitle());
        toDo.setMemo(toDoDto.getMemo());
        toDo.setImportant(toDoDto.isImportant());

        return toDo;
    }
}
