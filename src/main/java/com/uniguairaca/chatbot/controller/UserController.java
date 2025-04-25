package com.uniguairaca.chatbot.controller;

import com.uniguairaca.chatbot.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
