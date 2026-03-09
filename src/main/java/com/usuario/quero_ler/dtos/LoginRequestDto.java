package com.usuario.quero_ler.dtos;

import com.usuario.quero_ler.enuns.UsuarioProfile;

public record LoginRequestDto(
        String user,
        String  senha,
        UsuarioProfile profile
) {}
