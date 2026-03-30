package com.usuario.quero_ler.dtos.usuario;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UsuarioAtualizadoAdministradorRequest(

        @Past(message = "A data de nascimento deve estar no passado.")
        LocalDate dataDeNascimento,

        String cidade,

        String estado,

        String pais,

        byte[] foto
) {}