package com.usuario.quero_ler.service.implementacoes;

import com.usuario.quero_ler.dtos.autor.AutorRequest;
import com.usuario.quero_ler.dtos.livro.LivroRequest;
import com.usuario.quero_ler.dtos.livro.LivroResponse;
import com.usuario.quero_ler.exceptions.especies.IsbnNaoEncontradoException;
import com.usuario.quero_ler.mappers.LivroMapper;
import com.usuario.quero_ler.models.Autor;
import com.usuario.quero_ler.models.Livro;
import com.usuario.quero_ler.repository.LivroRepository;
import com.usuario.quero_ler.service.AutorServiceI;
import com.usuario.quero_ler.service.LivroServiceI;
import com.usuario.quero_ler.service.LoginServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class LivroServeceImpl implements LivroServiceI {

    private final LivroRepository repository;
    private final LivroMapper mapper;
    private final AutorServiceI autorServiceI;
    private final LoginServiceI loginServiceI;

    @Override
    public LivroResponse criar(LivroRequest dto) {
        loginServiceI.validarLogin();
        Livro livro = mapper.toEntity(dto);
        for (AutorRequest autorRequest : dto.autores()){
            Autor autor = autorServiceI.criar(autorRequest);
            livro.adicionarAutor(autor);
        }
        livro = repository.save(livro);

        return mapper.toResponse(livro);
    }

    @Override
    public Page<LivroResponse> listar(Pageable pageable) {
        loginServiceI.validarLogin();
        Page<LivroResponse> livros = repository.findAll(pageable).map(mapper::toResponse);
        return livros;
    }

    @Override
    public LivroResponse buscarPorIsbn(String isbn) {
        loginServiceI.validarLogin();
        Livro livro = repository.findByIsbn(isbn).orElseThrow(
                ()-> new IsbnNaoEncontradoException("Não há nenhum livro cadastrado com o código ISBN informado")
        );
        return mapper.toResponse(livro);
    }

    @Override
    public void inserirImagem(Long id, MultipartFile capaDoLivro) {

    }
}
