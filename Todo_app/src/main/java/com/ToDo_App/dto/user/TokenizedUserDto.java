package com.ToDo_App.dto.user;


import com.ToDo_App.dto.token.TokenDto;
import com.ToDo_App.dto.user.response.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class TokenizedUserDto extends UserDto {
    TokenDto tokens;
}
