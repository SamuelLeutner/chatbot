package com.uniguairaca.chatbot.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UserRequestDTO(
        @NotBlank(message = "Nome não pode ser vazio")
        @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
        String name,

        @Email(message = "Email inválido")
        @NotEmpty(message = "Email não pode ser vazio")
        String email
) {}
