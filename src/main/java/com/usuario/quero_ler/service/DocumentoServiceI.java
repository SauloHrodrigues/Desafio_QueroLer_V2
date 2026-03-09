package com.usuario.quero_ler.service;

import com.usuario.quero_ler.dtos.DocumentoAlteracoesDto;
import com.usuario.quero_ler.dtos.DocumentoRequestDto;
import com.usuario.quero_ler.dtos.DocumentoResponseDto;

public interface DocumentoServiceI {
    DocumentoResponseDto criar(DocumentoRequestDto dto);
    void alterar(Long id, DocumentoAlteracoesDto dto);
    DocumentoResponseDto getTermosGeraisDeUso();
}
