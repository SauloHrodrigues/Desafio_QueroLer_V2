package com.usuario.quero_ler.dtos.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequestDto(
        @NotBlank(message = "O usuário é de preenchimento obrigatório.")
        String user,
        @NotBlank(message = "A senha é de preenchimento obrigatório.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, com letra maiúscula, minúscula, número e caractere especial."
        )
        String senha
) {}