package com.usuario.quero_ler.fixtures;

import com.usuario.quero_ler.dtos.LoginRequestDto;
import com.usuario.quero_ler.enuns.UsuarioProfile;

public class LoginFixture {
    private static final String NOME = "Nome SobreNome";
    private static final String SENHA = "Teste123&";

    public static LoginRequestDto requestDto(UsuarioProfile profile) {
        return new LoginRequestDto(NOME, SENHA, profile);
    }
}
