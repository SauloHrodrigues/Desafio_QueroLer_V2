package com.usuario.quero_ler.Controllers;

import com.usuario.quero_ler.dtos.*;
import com.usuario.quero_ler.service.UsuarioServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioServiceI serviceI;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criar(@RequestBody @Valid UsuarioRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceI.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDoUsuarioResponse> dadosDoUsuario(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.getDadosDoUsuario(id));
    }

    @PutMapping("/{id}/dados-adicionais")
    public ResponseEntity<Void> inserirDadosAdicionais(@PathVariable Long id, @RequestBody @Valid UsuarioDadosComplementarRequest dto) {
        serviceI.adicionarDados(id, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@PathVariable Long id, @RequestBody @Valid DadosAtualizadosLeitorReguest dto) {
        serviceI.atualizarDados(id, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/administrador")
    public ResponseEntity<Void> alterar(@PathVariable Long id, @RequestBody @Valid DadosAtualizadosAdministradorReguest dto) {
        serviceI.atualizarDados(id, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> excluirPerfil() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
