package com.ToDo_App.utils.mapper;

import com.ToDo_App.data.models.User;
import com.ToDo_App.dto.user.request.RegisterRequestDto;
import com.ToDo_App.dto.user.UserDto;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;


//@Mapper(componentModel = "spring")
@Component
public class UserMapper {

    public static UserDto mapToUserDto(User user)
    {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUserName());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUserId(user.getUserId());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

    public static User mapToUser(
            UserDto userDto
    ){
        User user = new User();
        user.setUserName(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        return user;
    }

    public static UserDto mapsToUserDto(
            User user
    ){
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        return userDto;
    }

    public static User mapToUsers(
            RegisterRequestDto userDto, User user, BCrypt bCrypt
    ){

        user.setUserName(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(12)));
        return user;
    }
}
