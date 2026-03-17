package com.usuario.quero_ler.service;

import com.usuario.quero_ler.dtos.livro.LivroRequest;
import com.usuario.quero_ler.dtos.livro.LivroResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface LivroServiceI {
    LivroResponse criar(LivroRequest dto);
    Page<LivroResponse> listar(Pageable pageable);
    LivroResponse buscarPorIsbn(String isbn);
    void inserirImagem(Long id, MultipartFile capaDoLivro);
}