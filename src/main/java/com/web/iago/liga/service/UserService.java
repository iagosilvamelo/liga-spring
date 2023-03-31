package com.web.iago.liga.service;

import java.util.Arrays;
import java.util.List;

import com.web.iago.liga.dto.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserDto signUp(SignUpDto user) {
        return new UserDto(1L, "Sergio", "Lema", "login", "token");
    }

    public void signOut(UserDto user) {
        // nothing to do at the moment
    }
}
