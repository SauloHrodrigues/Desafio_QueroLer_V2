package com.usuario.quero_ler.dtos.usuario;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UsuarioRequestDto(
        @NotBlank(message = "O nome é de preenchimento obrigatório.")
        String nome,

        @NotBlank(message = "O e-mail é de preenchimento obrigatório.")
        @Email(message = "O e-mail informado é inválido.")
        String email,

        @NotBlank(message = "A confirmação do e-mail é obrigatória.")
        String confirmarEmail,

        @NotBlank(message = "A senha é de preenchimento obrigatório.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, com letra maiúscula, minúscula, número e caractere especial."
        )
        String senha,

        @NotBlank(message = "A confirmação da senha é obrigatória.")
        String confirmarSenha,

        @NotBlank(message = "O CPF é de preenchimento obrigatório.")
        @Pattern(
                regexp = "\\d{11}",
                message = "O CPF deve conter apenas 11 números."
        )
        String cpf,

        @NotNull(message = "A data de nascimento é obrigatória.")
        @Past(message = "A data de nascimento deve estar no passado.")
        LocalDate dataDeNascimento,

        @AssertTrue(message = "É necessário aceitar os termos.")
        Boolean checkTermo
) {}