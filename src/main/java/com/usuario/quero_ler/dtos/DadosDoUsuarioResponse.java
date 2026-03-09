package com.usuario.quero_ler.dtos;

import java.time.LocalDate;

public record DadosDoUsuarioResponse(
        String nome,
        String email,
        LocalDate dataDeNascimento,
        String cidade,
        String estado,
        String pais,
        byte[] foto
) {
}
