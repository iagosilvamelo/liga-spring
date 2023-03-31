package com.web.iago.liga.controller;

import com.web.iago.liga.constants.AuthPath;
import com.web.iago.liga.dto.SignUpDto;
import com.web.iago.liga.dto.UserDto;
import com.web.iago.liga.provider.UserAuthenticationProvider;
import com.web.iago.liga.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final UserService userService;

    public AuthController(UserAuthenticationProvider userAuthenticationProvider, UserService userService) {
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.userService = userService;
    }

    @PostMapping(AuthPath.SIGN_IN)
    public ResponseEntity<UserDto> signIn(@AuthenticationPrincipal UserDto user) {
        user.setToken(userAuthenticationProvider.createToken(user.getLogin()));
        return ResponseEntity.ok(user);
    }

    @PostMapping(AuthPath.SIGN_UP)
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpDto user) {
        UserDto createUser = userService.signUp(user);

        return ResponseEntity
                .created(URI.create("/users/" + createUser.getId() + "/profile"))
                .body(createUser);
    }

    @PostMapping(AuthPath.SIGN_OUT)
    public ResponseEntity<UserDto> signOut() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
}
