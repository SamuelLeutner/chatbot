package com.uniguairaca.chatbot.dto;

public record UserResponseDTO(
        Long id,
        String name,
        String email
) {
    public UserResponseDTO {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
    }
}
