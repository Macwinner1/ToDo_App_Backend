package com.ToDo_App.dto.token.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter @Getter
public class TokenRequestDto {

    @NotEmpty(message = "Refresh Token cannot be empty")
    private String refreshToken;
}
