package com.usuario.quero_ler.enuns;

public enum UsuarioPerfil {
    LEITOR("Leitor"),
    ADMINISTRADOR("Administrador"),
    MODERADOR("Moderador");

    private final String perfil;

    UsuarioPerfil(String perfil) {
        this.perfil = perfil;
    }
}