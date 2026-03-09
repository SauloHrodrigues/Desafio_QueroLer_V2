package com.usuario.quero_ler.service;

import com.usuario.quero_ler.dtos.NotificacaoRequestDto;
import com.usuario.quero_ler.dtos.NotificacaoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificacaoServiceI {
    NotificacaoResponseDto criar(NotificacaoRequestDto dto);
    Page<NotificacaoResponseDto> naoLidas(Pageable pageable);
}