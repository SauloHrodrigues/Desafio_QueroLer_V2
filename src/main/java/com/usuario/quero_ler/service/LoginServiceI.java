package com.usuario.quero_ler.service;

import com.usuario.quero_ler.dtos.LoginRequestDto;
import com.usuario.quero_ler.dtos.UsuarioRequestDto;
import com.usuario.quero_ler.enuns.UsuarioProfile;
import com.usuario.quero_ler.models.User;

public interface LoginServiceI {
    User criar(UsuarioRequestDto dto, UsuarioProfile profile);
    void login(LoginRequestDto dto);
    User validarLogin(Long id);
    Boolean validarLogin(User user);
}