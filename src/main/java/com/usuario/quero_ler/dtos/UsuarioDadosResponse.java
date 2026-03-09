package com.usuario.quero_ler.dtos;

import java.time.LocalDate;

public record UsuarioDadosResponse(
        String nome,
        String email,
        LocalDate dataDeNascimento,
        String cidade,
        String estado,
        String pais,
        byte[] foto
) {
}
