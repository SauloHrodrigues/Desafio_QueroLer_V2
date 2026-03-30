package com.usuario.quero_ler.dtos.documento;

import com.usuario.quero_ler.enuns.DocumentoTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DocumentoRequestDto(
        @NotBlank(message = "O título é de preenchimento obrigatório.")
        String titulo,
        @NotNull(message = "O tipo de documento é de preenchimento obrigatório.")
        DocumentoTipo tipo,
        @NotBlank(message = "O conteúdo do documento é de preenchimento obrigatório.")
        String conteudo
) {}