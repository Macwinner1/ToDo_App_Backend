package com.ToDo_App.services;

import com.ToDo_App.dto.token.TokenDto;
import com.ToDo_App.dto.token.request.TokenRequestDto;
import com.ToDo_App.dto.user.response.UserDto;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.Map;
import java.util.function.Function;

@Service
public interface JWTService {

    String extractUserName(String jwtToken, boolean isAccess);

    <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver, boolean isAccess);
    TokenDto generateJwtToken(UserDto userDto);
    boolean isTokenValid(String token, UserDto userDto, boolean isAccess);
    String generateAccessToken(Map<String, Object> extraClaims, UserDto userDto);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDto userDto);
    TokenDto generateJwtTokenFromRefreshToken(@Valid TokenRequestDto tokenRequest);
}
