package com.uniguairaca.chatbot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserCreateDTO {
    @NotEmpty(message = "Nome não pode ser vazio")
    private String name;

    @Email(message = "Email inválido")
    @NotEmpty(message = "Email não pode ser vazio")
    private String email;

    @NotEmpty(message = "Senha não pode ser vazia")
    @Size(min = 6, max = 255, message = "Senha deve ter pelo menos 6 caracteres e no máximo 255")
    private String password;
}
