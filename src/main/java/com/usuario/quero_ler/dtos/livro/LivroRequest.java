package com.usuario.quero_ler.dtos.livro;

import com.usuario.quero_ler.dtos.autor.AutorRequest;
import com.usuario.quero_ler.enuns.LivroIdioma;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record LivroRequest(
        @NotBlank
        @NotBlank(message = "O título do livro é de preenchimento obrigatório.")
        String titulo,

        @NotBlank
        @NotBlank(message = "O isbn do livro é de preenchimento obrigatório.")
        @Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN deve conter apenas números e ter 10 ou 13 dígitos")
        String isbn,

        @NotBlank
        @NotBlank(message = "A editora do livro é de preenchimento obrigatório.")
        String editora,

        @NotBlank
        @NotBlank(message = "O ano de publicação do livro é de preenchimento obrigatório.")
        String anoDePublicacao,

        @NotNull
        @NotBlank(message = "O número de páginas do livro é de preenchimento obrigatório.")
        Integer numeroDePaginas,

        @NotNull
        @NotBlank(message = "O idioma do livro é de preenchimento obrigatório.")
        LivroIdioma idioma,

        @NotBlank
        @Size(min = 50, message = "A sinopse deve ter no mínimo 50 caracteres")
        String sinopse,

        @Valid
        @NotEmpty(message = "Deve haver pelo menos um autor.")
        List<AutorRequest> autores
) {}