package com.ToDo_App.utils.mapper;

import com.ToDo_App.data.models.User;
import com.ToDo_App.dto.token.TokenDto;
import com.ToDo_App.dto.user.TokenizedUserDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;
import com.ToDo_App.dto.user.response.UserDto;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromUserDto(UserDto userDto);
    TokenizedUserDto toUserDto(User user, TokenDto tokenDto);

    default User fromRegisterDto(RegisterRequestDto registerRequestDto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUserName(registerRequestDto.getUsername());
        user.setFirstName(registerRequestDto.getFirstName());
        user.setLastName(registerRequestDto.getLastName());
        user.setPassword(BCrypt.hashpw(registerRequestDto.getPassword(), BCrypt.gensalt(12)));
        user.setEmail(registerRequestDto.getEmail());
        return user;
    }
}
