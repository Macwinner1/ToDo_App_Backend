package com.ToDo_App.services.impl;

import com.ToDo_App.config.properties.JWTConfigProperties;
import com.ToDo_App.dto.token.TokenDto;
import com.ToDo_App.dto.token.request.TokenRequestDto;
import com.ToDo_App.dto.user.response.UserDto;
import com.ToDo_App.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class IJWTService implements JWTService {
    @Autowired
    private JWTConfigProperties jwtConfigProperties;

    @Autowired
    private IUserService iUserService;

    private final SecretKey accessSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final SecretKey refreshSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long accessTokenExpiration = 1000 * 60 * 15; // 15 minutes
    private final long refreshTokenExpiration = 1000 * 60 * 60 * 24 * 7; // 7 days

    @Override
    public String extractUserName(String jwtToken, boolean isAccess) {
        return extractClaim(jwtToken, Claims::getSubject, isAccess);
    }

    @Override
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver, boolean isAccess) {
        Claims claims = extractAllClaims(jwtToken, isAccess);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, boolean isAccess) {
        SecretKey key = isAccess ? accessSecretKey : refreshSecretKey;
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isTokenValid(String token, UserDto userDto, boolean isAccess) {
        String username = extractUserName(token, isAccess);
        return username.equals(userDto.getUsername()) && !isTokenExpired(token, isAccess);
    }

    private boolean isTokenExpired(String token, boolean isAccess) {
        Date expiration = extractClaim(token, Claims::getExpiration, isAccess);
        return expiration.before(new Date());
    }

    @Override
    public String generateAccessToken(Map<String, Object> extraClaims, UserDto userDto) {
        return buildToken(extraClaims, userDto, accessTokenExpiration, accessSecretKey);
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDto userDto) {
        return buildToken(extraClaims, userDto, refreshTokenExpiration, refreshSecretKey);
    }

    private String buildToken(Map<String, Object> claims, UserDto userDto, long expiration, SecretKey key) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDto.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    @Override
    public TokenDto generateJwtToken(UserDto userDto) {
        Map<String, Object> claims = Map.of("email", userDto.getEmail());
        String accessToken = generateAccessToken(claims, userDto);
        String refreshToken = generateRefreshToken(claims, userDto);
        return new TokenDto(accessToken, refreshToken);
    }

    @Override
    public TokenDto generateJwtTokenFromRefreshToken(@Valid TokenRequestDto tokenRequest) {
        String refreshToken = tokenRequest.getRefreshToken();
        String username = extractUserName(refreshToken, false);
        if (isTokenExpired(refreshToken, false)) {
            throw new RuntimeException("Refresh token expired");
        }
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setEmail(extractClaim(refreshToken, claims -> claims.get("email", String.class), false));

        return generateJwtToken(userDto);
    }

}
