package com.usuario.quero_ler.dtos;

import java.time.LocalDate;

public record UsuarioAtualizadoAdministradorReguest(
        LocalDate dataDeNascimento,
        String cidade,
        String estado,
        String pais,
        byte[] foto
) {}