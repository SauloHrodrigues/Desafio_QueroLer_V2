package com.usuario.quero_ler.dtos;

import java.time.LocalDate;

public record UsuarioAlterarSenhaReguest(
        String senhaAtual,
        String senhaNova
) {}