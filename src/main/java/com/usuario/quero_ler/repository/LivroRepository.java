package com.usuario.quero_ler.repository;

import com.usuario.quero_ler.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro,Long> {
    Optional<Livro> findByIsbn(String isbn);
}
