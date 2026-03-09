package com.usuario.quero_ler.service.implementacoes;

import com.usuario.quero_ler.dtos.DocumentoAlteracoesDto;
import com.usuario.quero_ler.dtos.DocumentoRequestDto;
import com.usuario.quero_ler.dtos.DocumentoResponseDto;
import com.usuario.quero_ler.service.DocumentoServiceI;
import org.springframework.stereotype.Service;

@Service
public class DocumentoServiceImpl implements DocumentoServiceI {
    @Override
    public DocumentoResponseDto criar(DocumentoRequestDto dto) {
        return null;
    }

    @Override
    public void alterar(Long id, DocumentoAlteracoesDto dto) {

    }

    @Override
    public DocumentoResponseDto getTermosGeraisDeUso() {
        return null;
    }
}
