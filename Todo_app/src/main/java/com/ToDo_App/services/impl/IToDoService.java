package com.ToDo_App.services.impl;

import com.ToDo_App.data.models.ToDo;
import com.ToDo_App.data.models.User;
import com.ToDo_App.data.repository.ToDoRepository;
import com.ToDo_App.dto.todo.ToDoDto;
import com.ToDo_App.dto.todo.request.ToDoCreateOrUpdateRequestDto;
import com.ToDo_App.exceptions.ResourceNotFoundException;
import com.ToDo_App.exceptions.UnauthorizedActionException;
import com.ToDo_App.exceptions.UserAlreadyExistsException;
import com.ToDo_App.exceptions.UserNotAuthenticatedException;
import com.ToDo_App.services.ToDoService;
import com.ToDo_App.utils.mapper.ToDoMapper;
import com.ToDo_App.utils.mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IToDoService implements ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private ToDoMapper toDoMapper;



    @Override
    public List<ToDoDto> fetchAllToDos(User user){
        return toDoRepository.getAllToDosFromUser(user)
                .stream().map(toDoMapper::toToDoDto).toList();
    }

    @Override
    public ToDoDto fetchToDoById(User user, UUID todoId){
        ToDo todo = toDoRepository.getToDoById(user, todoId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Todo", "todoId",todoId.toString()
                )
        );
        return toDoMapper.toToDoDto(todo);
    }

    @Override
    public ToDoDto createToDo(ToDoCreateOrUpdateRequestDto request, User user) {
        ToDo todo = ToDoMapper.fromToDoCreateOrUpdateDto(request);
        todo.setUser(user);
        toDoRepository.save(todo);
        return toDoMapper.toToDoDto(todo);
    }

    @Override
    public ToDoDto updateToDo(UUID todoId, ToDoCreateOrUpdateRequestDto toDoDto, HttpSession httpSession) {
        User user = getAuthenticatedUser(httpSession).orElseThrow(
                () -> new UserAlreadyExistsException("User not authenticated")
        );
        ToDo todo = toDoRepository.getToDoById(user, todoId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Todo","todoId",todoId.toString()
                )
        );
        ToDo updatedToDo = ToDoMapper.fromToDoCreateOrUpdateRequestDto(toDoDto, todo);
        toDoRepository.save(updatedToDo);
        return toDoMapper.toToDoDto(updatedToDo);
    }

    @Override
    @Transactional
    public boolean deleteToDo(UUID todoId, HttpSession httpSession) {
        User sessionUser = getAuthenticatedUser(httpSession)
                .orElseThrow(() -> new UserNotAuthenticatedException("User not authenticated"));

        ToDo toDo = toDoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("ToDo", "todoId", todoId.toString()));

        if (!toDo.getUser().getUserId().equals(sessionUser.getUserId())) {
            throw new UnauthorizedActionException("You are not allowed to delete this ToDo.");
        }

        toDoRepository.delete(toDo);
        return true;
    }


    @Override
    public ToDoDto markAsCompleted(UUID todoId, HttpSession httpSession) {
        User user = getAuthenticatedUser(httpSession).orElseThrow(
                () -> new UserAlreadyExistsException("User not authenticated")
        );
        ToDo toDo = toDoRepository.getToDoById(user, todoId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Todo","todoId",todoId.toString()
                )
        );
        toDo.setCompleted(true);
        toDo.setCompletedAt(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        toDoRepository.save(toDo);
        return toDoMapper.toToDoDto(toDo);
    }
    @Override
    public ToDoDto markAsIncomplete(UUID todoId, HttpSession httpSession) {
        User user = getAuthenticatedUser(httpSession).orElseThrow(
                () -> new UserAlreadyExistsException("User not authenticated")
        );
        ToDo toDo = toDoRepository.getToDoById(user, todoId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Todo","todoId",todoId.toString()
                )
        );
        toDo.setCompleted(false);
        toDo.setCompletedAt(null);
        toDoRepository.save(toDo);
        return toDoMapper.toToDoDto(toDo);
    }

    @Override
    public List<ToDoDto> searchToDos(String searchTerm, boolean completed, HttpSession httpSession) {
        User user = getAuthenticatedUser(httpSession).orElseThrow(
                () -> new UserAlreadyExistsException("User not authenticated")
        );

        return toDoRepository.searchTodos(user,searchTerm,completed)
                .stream().map(toDoMapper::toToDoDto).toList();
    }

    public Optional<User> getAuthenticatedUser(HttpSession session) {
        Object obj = session.getAttribute("user");
        if (obj instanceof User user) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

}
