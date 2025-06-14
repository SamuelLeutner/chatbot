package com.uniguairaca.chatbot.mapper;

import com.uniguairaca.chatbot.dto.response.UserResponseDTO;
import com.uniguairaca.chatbot.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}