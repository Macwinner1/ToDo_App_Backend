package com.ToDo_App.dto.token;


import lombok.*;

@Data
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class TokenDto {
    String accessToken;
    String refreshToken;
}
