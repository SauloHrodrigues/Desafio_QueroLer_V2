package com.usuario.quero_ler.dtos.notificacao;

import jakarta.validation.constraints.NotBlank;

public record NotificacaoRequestDto(
        @NotBlank(message = "O texto da notificação é de preenchimento obrigatório.")
        String notificacao
) {}