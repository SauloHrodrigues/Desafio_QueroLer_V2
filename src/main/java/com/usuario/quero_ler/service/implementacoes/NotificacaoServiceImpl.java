package com.usuario.quero_ler.service.implementacoes;

import com.usuario.quero_ler.dtos.NotificacaoRequestDto;
import com.usuario.quero_ler.dtos.NotificacaoResponseDto;
import com.usuario.quero_ler.service.NotificacaoServiceI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoServiceImpl implements NotificacaoServiceI {
    @Override
    public NotificacaoResponseDto criar(NotificacaoRequestDto dto) {
        return null;
    }

    @Override
    public Page<NotificacaoResponseDto> naoLidas(Pageable pageable) {
        return null;
    }
}
