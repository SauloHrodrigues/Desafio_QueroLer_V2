package com.usuario.quero_ler.Controllers;

import com.usuario.quero_ler.dtos.livro.LivroRequest;
import com.usuario.quero_ler.dtos.livro.LivroResponse;
import com.usuario.quero_ler.service.LivroServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroController {
    private final LivroServiceI serviceI;

    @PostMapping
    ResponseEntity<LivroResponse> criar(@RequestBody @Valid LivroRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceI.criar(dto));
    }

    @GetMapping
    ResponseEntity<Page<LivroResponse>> listar(Pageable pageable){
        return ResponseEntity.ok(serviceI.listar(pageable));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<LivroResponse> buscar(@PathVariable String isbn){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorIsbn(isbn));
    }

    @PutMapping("/{id}/capa")
    public ResponseEntity<Void> inserirFoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        serviceI.inserirImagem(id, file);
        return ResponseEntity.noContent().build();
    }
}
