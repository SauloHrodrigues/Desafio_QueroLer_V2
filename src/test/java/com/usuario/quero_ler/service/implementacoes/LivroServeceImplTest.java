package com.usuario.quero_ler.service.implementacoes;

import com.usuario.quero_ler.dtos.livro.BuscaDeLivrosRequest;
import com.usuario.quero_ler.dtos.livro.LivroRequest;
import com.usuario.quero_ler.dtos.livro.LivroResponse;
import com.usuario.quero_ler.dtos.login.LoginRequestDto;
import com.usuario.quero_ler.enuns.TiposDeBusca;
import com.usuario.quero_ler.enuns.UsuarioProfile;
import com.usuario.quero_ler.fixtures.AutorFixture;
import com.usuario.quero_ler.fixtures.LivroFixture;
import com.usuario.quero_ler.fixtures.UserFixture;
import com.usuario.quero_ler.mappers.LivroMapper;
import com.usuario.quero_ler.models.Autor;
import com.usuario.quero_ler.models.Livro;
import com.usuario.quero_ler.models.User;
import com.usuario.quero_ler.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServeceImplTest {

    @InjectMocks
    private LivroServeceImpl service;

    @Mock
    private LivroRepository repository;

    @Mock
    private LivroMapper mapper;

    @Mock
    private AutorServiceImpl autorService;

    @Mock
    private LoginServiceImpl login;

    @Mock
    private MultipartFile multipartFile;

    private User leitor;
    private User admin;

    @BeforeEach
    void setUp() {
        leitor = UserFixture.userEntity(UsuarioProfile.LEITOR);
        admin = UserFixture.userEntity(UsuarioProfile.ADMINISTRADOR);
    }

    @Test
    @DisplayName("Deve criar um livro com sucesso.")
    void deveCriarUmLivroComSucesso() {
        LivroRequest dto = LivroFixture.request();
        Livro livro = LivroFixture.entity();
        LivroResponse response = LivroFixture.response();

        when(login.validarLogin()).thenReturn(leitor);
        when(mapper.toEntity(dto)).thenReturn(livro);
        when(autorService.criar(dto.autores().get(0))).thenReturn(livro.getAutores().get(0));

        when(repository.save(livro)).thenReturn(livro);
        when(mapper.toResponse(livro)).thenReturn(response);

        LivroResponse resultado = service.criar(dto, null);

        assertNotNull(resultado.id());
        assertEquals(dto.titulo(), resultado.titulo());
        assertEquals(dto.isbn(), resultado.isbn());
        assertEquals(dto.editora(), resultado.editora());
        assertEquals(dto.anoDePublicacao(), resultado.anoDePublicacao());
        assertEquals(dto.numeroDePaginas(), resultado.numeroDePaginas());
        assertEquals(dto.idioma(), resultado.idioma());
        assertEquals(dto.sinopse(), resultado.sinopse());
        assertEquals("/livros/" + resultado.id() + "/capa", resultado.capaUrl());

        verify(mapper).toEntity(dto);
        verify(repository).save(livro);
    }


    @Test
    @DisplayName("Deve retornar uma lista de livro com sucesso.")
    void deveRetornarUmalistaDeLivros() {
        Pageable pageable = PageRequest.of(0, 10);
        Livro livro01 = LivroFixture.entity();
        Livro livro02 = LivroFixture.entity();
        LivroResponse response = LivroFixture.response();
        List<Livro> livros = List.of(livro01, livro02);
        Page<Livro> pageLivros = new PageImpl<>(livros, pageable, livros.size());

        when(login.validarLogin()).thenReturn(leitor);
        when(repository.findAll(pageable)).thenReturn(pageLivros);
        when(mapper.toResponse(livro01)).thenReturn(response);

        Page<LivroResponse> resultado = service.listar(pageable);

        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());

        verify(login).validarLogin();
        verify(repository).findAll(pageable);
        verify(mapper).toResponse(livro01);
    }

    @Test
    @DisplayName("Deve buscar um livro por ISBN com sucesso.")
    void deveBuscarPorIsbn() {
        Pageable pageable = PageRequest.of(0, 10);
        BuscaDeLivrosRequest dto = LivroFixture.buscaDeLivrosRequest(TiposDeBusca.ISBN);
        String isbn = dto.valor();
        Livro livro = LivroFixture.entity();
        LivroResponse response = LivroFixture.response();

        when(repository.findByIsbn(isbn)).thenReturn(Optional.of(livro));
        when(mapper.toResponse(livro)).thenReturn(response);

        LivroResponse resultado = (LivroResponse) service.buscar(dto, pageable);

        assertEquals(dto.valor(), resultado.isbn());
        verify(repository).findByIsbn(dto.valor());
    }

    @Test
    @DisplayName("Deve buscar um livro por autor com sucesso.")
    void deveBuscarPorAutor() {
        BuscaDeLivrosRequest dto = LivroFixture.buscaDeLivrosRequest(TiposDeBusca.AUTOR);
        String autor = dto.valor();
        Livro livro01 = LivroFixture.entity();
        Livro livro02 = LivroFixture.entity();
        List<Livro> livros = List.of(livro01, livro02);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Livro> pageLivros = new PageImpl<>(livros, pageable, livros.size());
        LivroResponse response = LivroFixture.response();

        when(repository.findDistinctByAutoresNomeContainingIgnoreCase(autor, pageable)).thenReturn(pageLivros);
        when(mapper.toResponse(any())).thenReturn(response);
        Page<LivroResponse> resultado = (Page<LivroResponse>) service.buscar(dto, pageable);

        assertEquals(2, resultado.getContent().size());
        assertNotNull(resultado.getContent().get(0));
        assertNotNull(resultado.getContent().get(1));

        verify(mapper).toResponse(livro01);
        verify(mapper).toResponse(livro02);
        verify(repository).findDistinctByAutoresNomeContainingIgnoreCase(autor, pageable);
    }

    @Test
    @DisplayName("Deve buscar um livro por TITULO com sucesso.")
    void deveBuscarPorTitulo() {
        BuscaDeLivrosRequest dto = LivroFixture.buscaDeLivrosRequest(TiposDeBusca.TITULO);
        String titulo = dto.valor();
        Livro livro01 = LivroFixture.entity();
        Livro livro02 = LivroFixture.entity();
        List<Livro> livros = List.of(livro01, livro02);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Livro> pageLivros = new PageImpl<>(livros, pageable, livros.size());
        LivroResponse response = LivroFixture.response();

        when(repository.findByTituloContainingIgnoreCase(titulo, pageable)).thenReturn(pageLivros);
        when(mapper.toResponse(any())).thenReturn(response);
        Page<LivroResponse> resultado = (Page<LivroResponse>) service.buscar(dto, pageable);

        assertEquals(2, resultado.getContent().size());
        assertTrue(titulo.equalsIgnoreCase(resultado.getContent().get(0).titulo()));
        assertTrue(titulo.equalsIgnoreCase(resultado.getContent().get(1).titulo()));
        assertNotNull(resultado.getContent().get(0));
        assertNotNull(resultado.getContent().get(1));

        verify(mapper).toResponse(livro01);
        verify(mapper).toResponse(livro02);
        verify(repository).findByTituloContainingIgnoreCase(titulo, pageable);
    }

    @Test
    @DisplayName("Deve buscar um livro por EDITORA com sucesso.")
    void deveBuscarPorEditora() {
        BuscaDeLivrosRequest dto = LivroFixture.buscaDeLivrosRequest(TiposDeBusca.EDITORA);
        String editora = dto.valor();
        Livro livro01 = LivroFixture.entity();
        Livro livro02 = LivroFixture.entity();
        List<Livro> livros = List.of(livro01, livro02);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Livro> pageLivros = new PageImpl<>(livros, pageable, livros.size());
        LivroResponse response = LivroFixture.response();

        when(repository.findByEditoraContainingIgnoreCase(editora, pageable)).thenReturn(pageLivros);
        when(mapper.toResponse(any())).thenReturn(response);
        Page<LivroResponse> resultado = (Page<LivroResponse>) service.buscar(dto, pageable);

        assertEquals(2, resultado.getContent().size());
        assertTrue(editora.equalsIgnoreCase(resultado.getContent().get(0).editora()));
        assertTrue(editora.equalsIgnoreCase(resultado.getContent().get(1).editora()));
        assertNotNull(resultado.getContent().get(0));
        assertNotNull(resultado.getContent().get(1));

        verify(mapper).toResponse(livro01);
        verify(mapper).toResponse(livro02);
        verify(repository).findByEditoraContainingIgnoreCase(editora, pageable);
    }

    @Test
    @DisplayName("Deve inserir capa do livro com sucesso")
    void deveInserirCapaDoLivroComSucesso() throws Exception {
        Livro livro = LivroFixture.entity();
        Long id = livro.getId();

        byte[] imagem = LivroFixture.entityComCapa().getCapaDoLivro();

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "capa.jpg",
                "image/jpeg",
                imagem
        );

        when(repository.findById(id)).thenReturn(Optional.of(livro));

        service.inserirCapaDoLivro(id, file);

        assertArrayEquals(imagem, livro.getCapaDoLivro());
        verify(repository).save(livro);
    }

    @Test
    void buscarCapa() {
    }

    @Test
    void buscarIsbn() {
    }

    @Test
    void buscarAutor() {
    }

    @Test
    void buscarPorTitulo() {
    }

    @Test
    void buscarPorEditora() {
    }

    @Test
    void validarCapaDoLivro() {
    }
}