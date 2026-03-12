package com.usuario.quero_ler.dtos.login;

import com.usuario.quero_ler.enuns.UsuarioProfile;

public record LoginRequestDto(
        String user,
        String  senha,
        UsuarioProfile profile
) {}
