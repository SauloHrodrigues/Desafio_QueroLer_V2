
package com.usuario.quero_ler.service;

import com.usuario.quero_ler.dtos.*;

public interface UsuarioServiceI {
    UsuarioResponseDto criar(UsuarioRequestDto dto);
    void adicionarDados(Long id, UsuarioDadosComplementarRequest dto);
    DadosDoUsuarioResponse getDadosDoUsuario(Long id);
    void atualizarDados(Long id, DadosAtualizadosLeitorReguest dto);
    void atualizarDados(Long id, DadosAtualizadosAdministradorReguest dto);
    void excluirPerfil();
}
