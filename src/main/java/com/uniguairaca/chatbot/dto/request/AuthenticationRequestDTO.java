package com.uniguairaca.chatbot.dto.request;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDTO(
        @NotBlank(message = "Nome de usuário não pode ser vazio")
        @Size(min = 2, max = 100, message = "Nome de usuário deve ter entre 2 e 100 caracteres")
        String username,

        @NotBlank(message = "Senha não pode ser vazia")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String password
) {}
