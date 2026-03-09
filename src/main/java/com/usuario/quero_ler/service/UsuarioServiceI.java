
package com.usuario.quero_ler.service;

import com.usuario.quero_ler.dtos.*;

public interface UsuarioServiceI {
    UsuarioResponseDto criar(UsuarioRequestDto dto);
    void adicionarDados(Long id, UsuarioDadosComplementarRequest dto);
    UsuarioDadosResponse getDadosDoUsuario(Long id);
    void atualizar(Long id, UsuarioAtualizadoLeitorReguest dto);
    void atualizar(Long id, UsuarioAtualizadoAdministradorReguest dto);
    void excluirPerfil(Long id);
}
