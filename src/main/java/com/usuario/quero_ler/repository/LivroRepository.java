package com.usuario.quero_ler.repository;

import com.usuario.quero_ler.models.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByIsbn(String isbn);

    Page<Livro> findDistinctByAutoresNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    Page<Livro> findByEditoraContainingIgnoreCase(String editora, Pageable pageable);
}
