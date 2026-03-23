package com.usuario.quero_ler.service.implementacoes;

import com.usuario.quero_ler.dtos.autor.AutorRequest;
import com.usuario.quero_ler.dtos.livro.BuscaDeLivrosRequest;
import com.usuario.quero_ler.dtos.livro.LivroRequest;
import com.usuario.quero_ler.dtos.livro.LivroResponse;
import com.usuario.quero_ler.exceptions.especies.CapaForaDePadraoException;
import com.usuario.quero_ler.exceptions.especies.CapaNaoCadastradaException;
import com.usuario.quero_ler.exceptions.especies.IsbnNaoEncontradoException;
import com.usuario.quero_ler.exceptions.especies.LivroNaoEncontradoException;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LivroServeceImpl implements LivroServiceI {

    private final LivroRepository repository;
    private final LivroMapper mapper;
    private final AutorServiceI autorServiceI;
    private final LoginServiceI loginServiceI;

    @Override
    public LivroResponse criar(LivroRequest dto,MultipartFile capaDoLivro) {
        loginServiceI.validarLogin();

        Livro livro = mapper.toEntity(dto);
        for (AutorRequest autorRequest : dto.autores()){
            Autor autor = autorServiceI.criar(autorRequest);
            livro.adicionarAutor(autor);
        }
        if (capaDoLivro != null && !capaDoLivro.isEmpty()){
            validarCapaDoLivro(capaDoLivro);
            try {
                livro.setCapaDoLivro(capaDoLivro.getBytes());
            } catch (IOException e) {
                throw new CapaForaDePadraoException("Erro ao ler imagem"+ e);
            }
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
    public Object buscar(BuscaDeLivrosRequest dto, Pageable pageable) {
        return switch (dto.tiposDeBusca()){
            case ISBN -> buscarIsbn(dto.valor());
            case AUTOR -> buscarAutor(dto.valor(),pageable);
            case TITULO -> buscarPorTitulo(dto.valor(),pageable);
            case EDITORA -> buscarPorEditora(dto.valor(), pageable);
        };
    }

    @Override
    public void inserirCapaDoLivro(Long id, MultipartFile capaDoLivro) {
        validarCapaDoLivro(capaDoLivro);
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado"));

        try {
            livro.setCapaDoLivro(capaDoLivro.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler imagem", e);
        }

        repository.save(livro);
    }

    @Override
    public byte[] buscarCapa(Long id){
        Livro livro = repository.findById(id).orElseThrow(
                ()-> new LivroNaoEncontradoException("Livro não encontrado")
        );

        if (livro.getCapaDoLivro()== null){
            throw new CapaNaoCadastradaException("Capa não cadastrada");
        } else {
            return livro.getCapaDoLivro();
        }
    }

    protected LivroResponse buscarIsbn(String isbn){
        Livro livro =    livro = repository.findByIsbn(isbn).orElseThrow(
                ()-> new IsbnNaoEncontradoException("Não há nenhum livro cadastrado com o código ISBN informado")
        );
        return mapper.toResponse(livro);
    }

    protected Page<LivroResponse> buscarAutor(String autor, Pageable pageable){
        return repository
                .findDistinctByAutoresNomeContainingIgnoreCase(autor, pageable)
                .map(mapper::toResponse);
    }

    protected Page<LivroResponse> buscarPorTitulo(String titulo, Pageable pageable){
        return repository
                .findByTituloContainingIgnoreCase(titulo, pageable)
                .map(mapper::toResponse);
    }

    protected Page<LivroResponse> buscarPorEditora(String editora, Pageable pageable){
        return repository
                .findByEditoraContainingIgnoreCase(editora, pageable)
                .map(mapper::toResponse);
    }



    protected void validarCapaDoLivro(MultipartFile capaDoLivro) {
        try {
            if (capaDoLivro == null || capaDoLivro.isEmpty()) {
                return;
            }

            long tamanhoMaximo = 10 * 1024 * 1024;
            if (capaDoLivro.getSize() > tamanhoMaximo) {
                throw new CapaForaDePadraoException("Imagem excede o tamanho máximo de 10MB");
            }

            List<String> tiposPermitidos = List.of(
                    "image/jpeg",
                    "image/jpg",
                    "image/png"
            );

            if (capaDoLivro.getContentType() == null ||
                    !tiposPermitidos.contains(capaDoLivro.getContentType())) {
                throw new CapaForaDePadraoException("Formato inválido. Use JPG ou PNG");
            }

            BufferedImage imagem = ImageIO.read(capaDoLivro.getInputStream());
            if (imagem == null) {
                throw new CapaForaDePadraoException("Arquivo enviado não é uma imagem válida");
            }

        } catch (IOException e) {
            throw new CapaForaDePadraoException("Erro ao processar imagem");
        }
    }
}