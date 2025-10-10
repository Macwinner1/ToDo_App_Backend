package com.ToDo_App.dto.user.response;


import com.ToDo_App.dto.BaseResponseDto;
import com.ToDo_App.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
public class UserResponseDto extends BaseResponseDto {

    public UserResponseDto(HttpStatus statusCode, String statusMessage, UserDto data) {
        super(statusCode,statusMessage, data);
    }
}
