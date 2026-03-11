package com.usuario.quero_ler.mappers;

import com.usuario.quero_ler.dtos.DocumentoAlteracoesDto;
import com.usuario.quero_ler.dtos.DocumentoRequestDto;
import com.usuario.quero_ler.dtos.DocumentoResponseDto;
import com.usuario.quero_ler.models.Documento;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DocumentoMapper {
    public Documento toEntity(DocumentoRequestDto dto){
        Documento documento =  new Documento();
        documento.setTitulo(dto.titulo());
        documento.setTipo(dto.tipo());
        documento.setConteudo(dto.conteudo());
        documento.setUltimaAlteracao(LocalDateTime.now());
        return documento;
    }

    public DocumentoResponseDto toResponse(Documento documento){
        return new DocumentoResponseDto(
                documento.getId(),
                documento.getTitulo(),
                documento.getTipo(),
                documento.getConteudo(),
                documento.getUltimaAlteracao()
        );
    }

    public  Documento toUpdate(Documento documento, DocumentoAlteracoesDto atualizacoes){
       LocalDateTime agora = LocalDateTime.now();
       documento.setUltimaAlteracao(agora);
        documento.setTitulo(atualizacoes.titulo() != null ? atualizacoes.titulo() : documento.getTitulo());
       documento.setTipo(atualizacoes.tipo() != null ? atualizacoes.tipo() : documento.getTipo());
       documento.setConteudo(atualizacoes.conteudo()!= null ? atualizacoes.conteudo() : documento.getConteudo());
       return documento;
    }
}
