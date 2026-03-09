package com.usuario.quero_ler.Controllers;

import com.usuario.quero_ler.dtos.NotificacaoRequestDto;
import com.usuario.quero_ler.dtos.NotificacaoResponseDto;
import com.usuario.quero_ler.service.NotificacaoServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {
    private NotificacaoServiceI serviceI;

    @PostMapping
    public ResponseEntity<NotificacaoResponseDto> criar(NotificacaoRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceI.criar(dto));
    }

    @GetMapping
    public ResponseEntity<Page<NotificacaoResponseDto>> naoLidas(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.naoLidas(pageable));
    }
}
