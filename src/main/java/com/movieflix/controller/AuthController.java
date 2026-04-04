package com.movieflix.controller;

import com.movieflix.config.TokenService;
import com.movieflix.entity.User;
import com.movieflix.exception.UsernameOrPasswordInvalidException;
import com.movieflix.mapper.UserMapper;
import com.movieflix.request.LoginRequest;
import com.movieflix.request.UserRequest;
import com.movieflix.response.LoginResponse;
import com.movieflix.response.MovieResponse;
import com.movieflix.response.UserResponse;
import com.movieflix.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movieflix/auth")
@RequiredArgsConstructor
@Tag(name = "User", description = "Resource responsable for registers of new users and login")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Operation(summary = "Register a new user", description = "Method to register a new user",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Successfully found movies by category",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        User savedUser = userService.save(UserMapper.toUser(userRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(savedUser));
    }

    @Operation(summary = "Login with a created user", description = "Method to login with existing credentials",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Successfully logged",
            content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        User user = (User) authentication.getPrincipal();

        String token = tokenService.geneteToken(user);

        return ResponseEntity.ok(new LoginResponse(token));

        } catch(BadCredentialsException e) {
            throw new  UsernameOrPasswordInvalidException("Usuário ou senha inválido.");
        }
    }

}
