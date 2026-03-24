package com.usuario.quero_ler.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usuario.quero_ler.dtos.livro.BuscaDeLivrosRequest;
import com.usuario.quero_ler.dtos.livro.LivroRequest;
import com.usuario.quero_ler.dtos.livro.LivroResponse;
import com.usuario.quero_ler.dtos.notificacao.NotificacaoResponseDto;
import com.usuario.quero_ler.dtos.usuario.UsuarioDadosResponse;
import com.usuario.quero_ler.dtos.usuario.UsuarioRequestDto;
import com.usuario.quero_ler.enuns.TiposDeBusca;
import com.usuario.quero_ler.enuns.UsuarioProfile;
import com.usuario.quero_ler.fixtures.LivroFixture;
import com.usuario.quero_ler.fixtures.NotificacaoFixture;
import com.usuario.quero_ler.fixtures.UserFixture;
import com.usuario.quero_ler.models.Livro;
import com.usuario.quero_ler.models.User;
import com.usuario.quero_ler.models.Usuario;
import com.usuario.quero_ler.service.LivroServiceI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LivroController.class)
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LivroServiceI serviceI;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve cadastrar um novo livro sem capa com sucesso")
    void deveCadastrarUmNovoLivroSemCapa() throws Exception {
        LivroRequest dto = LivroFixture.request();
        String json = objectMapper.writeValueAsString(dto);

        MockMultipartFile dados = new MockMultipartFile(
                "dados",
                "",
                "application/json",
                json.getBytes()
        );

        mockMvc.perform(multipart("/livros")
                        .file(dados))
                .andExpect(status().isCreated());

        verify(serviceI).criar(any(LivroRequest.class), isNull());

    }

    @Test
    @DisplayName("Deve cadastrar um novo livro com capa, com sucesso")
    void deveCadastrarUmNovoLivroComCapa() throws Exception {
        LivroRequest dto = LivroFixture.request();
        byte[] imagem = LivroFixture.entityComCapa().getCapaDoLivro();
        String json = objectMapper.writeValueAsString(dto);

        MockMultipartFile capaDoLivro = new MockMultipartFile(
                "imagem",
                "capa.jpg",
                "image/jpeg",
                imagem
        );
        MockMultipartFile dados = new MockMultipartFile(
                "dados",
                "",
                "application/json",
                json.getBytes()
        );

        mockMvc.perform(multipart("/livros")
                        .file(dados)
                        .file(capaDoLivro))
                .andExpect(status().isCreated());

        verify(serviceI).criar(dto, capaDoLivro);

    }

    @Test
    @DisplayName("Deve retornar todos livros do banco com sucesso")
    void deveRetornarTodosOsLivrosDoBanco() throws Exception {
        LivroResponse livro = LivroFixture.response();
        Pageable pageable = PageRequest.of(0, 20);

        Page<LivroResponse> page = new PageImpl<>(List.of(livro,livro),pageable,2);

        when(serviceI.listar(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/livros")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(livro.id()))
                .andExpect(jsonPath("$.content[0].editora").value(livro.editora()))
                .andExpect(jsonPath("$.content[0].titulo").value(livro.titulo()))
                .andExpect(jsonPath("$.content[1].id").value(livro.id()))
                .andExpect(jsonPath("$.content[1].editora").value(livro.editora()))
                .andExpect(jsonPath("$.content[1].titulo").value(livro.titulo()))
        ;

        verify(serviceI).listar(any(Pageable.class));;
    }

    @Test
    @DisplayName("Deve buscar um livro pelo isbn com sucesso")
    void deveBuscarLivroPorIsbn() {
    }

        @Test
        @DisplayName("Deve buscar capa do livro por id com sucesso")
        void deveRetornaACapaDoLivro() throws Exception {

            Long id = 1L;
            byte[] imagem = LivroFixture.entityComCapa().getCapaDoLivro();

            when(serviceI.buscarCapa(id)).thenReturn(imagem);

            mockMvc.perform(get("/livros/{id}/capa", id))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                    .andExpect(content().bytes(imagem));

            verify(serviceI).buscarCapa(id);
        }

    @Test
    @DisplayName("Deve inserir capa do livro com sucesso")
    void deveInserirCapaDoLivro() throws Exception {

        Long id = 1L;
        byte[] imagem = "imagem fake".getBytes();

        MockMultipartFile capa = new MockMultipartFile(
                "imagem",
                "capa.jpg",
                "image/jpeg",
                imagem
        );

        doNothing().when(serviceI).inserirCapaDoLivro(anyLong(), any());

        mockMvc.perform(multipart("/livros/{id}/capa", id)
                        .file(capa)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isNoContent());

        verify(serviceI).inserirCapaDoLivro(eq(id), any(MultipartFile.class));
    }
}