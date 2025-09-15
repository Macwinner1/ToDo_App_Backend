package com.ToDo_App.utils.mapper;

import com.ToDo_App.data.models.ToDo;
import com.ToDo_App.data.models.User;
import com.ToDo_App.dto.todo.ToDoDto;
import com.ToDo_App.dto.todo.request.ToDoCreateOrUpdateRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToDoMapper {
    ToDo fromToDoDto(ToDoDto toDoDto);
    ToDoDto toToDoDto(ToDo toDo);
    default ToDo fromToDoCreateOrUpdateDto(ToDoCreateOrUpdateRequestDto toDoDto, User user){
        ToDo toDo = new ToDo();
        toDo.setUser(user);
        toDo.setTitle(toDoDto.getTitle());
        toDo.setMemo(toDoDto.getMemo());
        toDo.setCompleted(false);
        toDo.setImportant(toDoDto.isImportant());

        return toDo;
    }
    default ToDo fromToDoCreateOrUpdateRequestDto(ToDoCreateOrUpdateRequestDto toDoDto, ToDo toDo){
        toDo.setTitle(toDoDto.getTitle());
        toDo.setMemo(toDoDto.getMemo());
        toDo.setImportant(toDoDto.isImportant());

        return toDo;
    }
}
