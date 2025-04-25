package com.uniguairaca.chatbot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserRequestDTO {
    @NotEmpty(message = "Nome não pode ser vazio")
    private String name;

    @Email(message = "Email inválido")
    @NotEmpty(message = "Email não pode ser vazio")
    private String email;
}
