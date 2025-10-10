package com.ToDo_App.services.impl;

import com.ToDo_App.data.models.ToDo;
import com.ToDo_App.data.models.User;
import com.ToDo_App.data.repository.ToDoRepository;
import com.ToDo_App.data.repository.UserRepository;
import com.ToDo_App.dto.todo.ToDoDto;
import com.ToDo_App.dto.todo.request.ToDoCreateOrUpdateRequestDto;
import com.ToDo_App.dto.todo.response.ListToDoResponseDto;
import com.ToDo_App.dto.todo.response.StatsResponseDto;
import com.ToDo_App.dto.todo.response.ToDoResponseDto;
import com.ToDo_App.exceptions.ResourceNotFoundException;
import com.ToDo_App.exceptions.UnauthorizedActionException;
import com.ToDo_App.exceptions.UserNotAuthenticatedException;
import com.ToDo_App.services.ToDoService;
import com.ToDo_App.utils.mapper.ToDoMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ToDoServiceImpl implements ToDoService {
    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;
    private final ToDoMapper toDoMapper;

    @Autowired
    public ToDoServiceImpl(ToDoRepository toDoRepository, UserRepository userRepository, ToDoMapper toDoMapper) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
        this.toDoMapper = toDoMapper;
    }

    private User getAuthenticatedUser(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            throw new UserNotAuthenticatedException("User not authenticated");
        }
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotAuthenticatedException("User not found with ID: " + userId));
    }

    private void checkTodoOwnership(ToDo toDo, User user) {
        if (!toDo.getUser().getUserId().equals(user.getUserId())) {
            throw new UnauthorizedActionException("User not authorized to access this todo");
        }
    }

    @Override
    public ToDoResponseDto createToDo(ToDoCreateOrUpdateRequestDto toDoCreateOrUpdateRequestDto, HttpSession session) {
        User user = getAuthenticatedUser(session);
        ToDo toDo = toDoMapper.toToDo(toDoCreateOrUpdateRequestDto);
        toDo.setUser(user);
        ToDo savedToDo = toDoRepository.save(toDo);
        return new ToDoResponseDto(HttpStatus.CREATED, "ToDo created successfully", toDoMapper.toToDoDto(savedToDo));
    }

    @Override
    public ToDoResponseDto updateToDo(String toDoId, ToDoCreateOrUpdateRequestDto toDoCreateOrUpdateRequestDto, HttpSession session) {
        User user = getAuthenticatedUser(session);
        ToDo toDo = toDoRepository.findById(UUID.fromString(toDoId))
                .orElseThrow(() -> new ResourceNotFoundException("ToDo", "toDoId", toDoId));
        checkTodoOwnership(toDo, user);
        toDo.setTitle(toDoCreateOrUpdateRequestDto.getTitle());
        toDo.setDescription(toDoCreateOrUpdateRequestDto.getDescription());
        toDo.setCompleted(toDoCreateOrUpdateRequestDto.isCompleted());
        toDo.setPriority(ToDo.Priority.valueOf(toDoCreateOrUpdateRequestDto.getPriority()));
        if (toDoCreateOrUpdateRequestDto.getDueDate() != null) {
            toDo.setDueDate(java.time.LocalDateTime.parse(toDoCreateOrUpdateRequestDto.getDueDate(), java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        ToDo updatedToDo = toDoRepository.save(toDo);
        return new ToDoResponseDto(HttpStatus.OK, "ToDo updated successfully", toDoMapper.toToDoDto(updatedToDo));
    }

    @Override
    public ToDoResponseDto fetchToDoById(String toDoId, HttpSession session) {
        User user = getAuthenticatedUser(session);
        ToDo toDo = toDoRepository.findById(UUID.fromString(toDoId))
                .orElseThrow(() -> new ResourceNotFoundException("ToDo", "toDoId", toDoId));
        checkTodoOwnership(toDo, user);
        return new ToDoResponseDto(HttpStatus.OK, "ToDo fetched successfully", toDoMapper.toToDoDto(toDo));
    }

    @Override
    public ListToDoResponseDto fetchAllToDos(HttpSession session) {
        User user = getAuthenticatedUser(session);
        List<ToDoDto> toDoDtos = toDoRepository.findByUser(user).stream()
                .map(toDoMapper::toToDoDto)
                .collect(Collectors.toList());
        return new ListToDoResponseDto(HttpStatus.OK, "ToDos fetched successfully", toDoDtos);
    }

    @Override
    public ListToDoResponseDto searchTodos(Boolean completed, String keyword, HttpSession session) {
        User user = getAuthenticatedUser(session);
        List<ToDoDto> toDoDtos = toDoRepository.searchTodos(user, completed, keyword).stream()
                .map(toDoMapper::toToDoDto)
                .collect(Collectors.toList());
        return new ListToDoResponseDto(HttpStatus.OK, "ToDos fetched successfully", toDoDtos);
    }

    @Override
    public ToDoResponseDto completeToDo(String toDoId, HttpSession session) {
        User user = getAuthenticatedUser(session);
        ToDo toDo = toDoRepository.findById(UUID.fromString(toDoId))
                .orElseThrow(() -> new ResourceNotFoundException("ToDo", "toDoId", toDoId));
        checkTodoOwnership(toDo, user);
        toDo.setCompleted(true);
        ToDo updatedToDo = toDoRepository.save(toDo);
        return new ToDoResponseDto(HttpStatus.OK, "ToDo marked as complete", toDoMapper.toToDoDto(updatedToDo));
    }

    @Override
    public ToDoResponseDto incompleteToDo(String toDoId, HttpSession session) {
        User user = getAuthenticatedUser(session);
        ToDo toDo = toDoRepository.findById(UUID.fromString(toDoId))
                .orElseThrow(() -> new ResourceNotFoundException("ToDo", "toDoId", toDoId));
        checkTodoOwnership(toDo, user);
        toDo.setCompleted(false);
        ToDo updatedToDo = toDoRepository.save(toDo);
        return new ToDoResponseDto(HttpStatus.OK, "ToDo marked as incomplete", toDoMapper.toToDoDto(updatedToDo));
    }

    @Override
    public void deleteToDo(String toDoId, HttpSession session) {
        User user = getAuthenticatedUser(session);
        ToDo toDo = toDoRepository.findById(UUID.fromString(toDoId))
                .orElseThrow(() -> new ResourceNotFoundException("ToDo", "toDoId", toDoId));
        checkTodoOwnership(toDo, user);
        toDoRepository.delete(toDo);
    }

    @Override
    public StatsResponseDto getTodoStats(HttpSession session) {
        User user = getAuthenticatedUser(session);
        long total = toDoRepository.countByUser(user);
        long completed = toDoRepository.countByUserAndCompleted(user, true);
        long pending = toDoRepository.countByUserAndCompleted(user, false);
        long highPriority = toDoRepository.countByUserAndPriority(user, ToDo.Priority.HIGH);
        StatsResponseDto.StatsData statsData = new StatsResponseDto.StatsData(total, completed, pending, highPriority);
        return new StatsResponseDto(HttpStatus.OK, "Stats fetched successfully", statsData);
    }
}