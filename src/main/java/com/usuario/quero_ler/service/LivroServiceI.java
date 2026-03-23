package com.usuario.quero_ler.service;

import com.usuario.quero_ler.dtos.livro.BuscaDeLivrosRequest;
import com.usuario.quero_ler.dtos.livro.LivroRequest;
import com.usuario.quero_ler.dtos.livro.LivroResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface LivroServiceI {
    LivroResponse criar(LivroRequest dto, MultipartFile capaDoLivro);
    Page<LivroResponse> listar(Pageable pageable);
    Object buscar(BuscaDeLivrosRequest dto, Pageable pageable);
    void inserirCapaDoLivro(Long id, MultipartFile capaDoLivro);
    byte[] buscarCapa(Long id);
}