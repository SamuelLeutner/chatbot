package com.uniguairaca.chatbot.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
        @NotBlank(message = "Nome não pode ser vazio")
        @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
        String name,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email não pode ser vazio")
        String email,

        @NotBlank(message = "Senha não pode ser vazia")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String password
) {}