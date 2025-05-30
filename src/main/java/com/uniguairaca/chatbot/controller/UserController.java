package com.uniguairaca.chatbot.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import com.uniguairaca.chatbot.dto.UserCreateDTO;
import com.uniguairaca.chatbot.dto.response.UserResponseDTO;

import org.springframework.web.bind.annotation.*;
import com.uniguairaca.chatbot.mapper.UserMapper;
import com.uniguairaca.chatbot.service.UserService;
import org.springframework.validation.annotation.Validated;

@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        return userMapper.toResponseDTO(userService.createUser(userCreateDTO));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserResponseDTO updateUser(@Valid @RequestBody UserCreateDTO userCreateDTO, Long userId) {
        return userMapper.toResponseDTO(userService.updateUser(userCreateDTO, userId));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
    }
}
