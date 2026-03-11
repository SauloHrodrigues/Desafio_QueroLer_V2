package com.usuario.quero_ler.service.implementacoes;

import com.usuario.quero_ler.dtos.DocumentoAlteracoesDto;
import com.usuario.quero_ler.dtos.DocumentoRequestDto;
import com.usuario.quero_ler.dtos.DocumentoResponseDto;
import com.usuario.quero_ler.enuns.UsuarioProfile;
import com.usuario.quero_ler.exceptions.especies.DocumentoNaoEncontradoException;
import com.usuario.quero_ler.exceptions.especies.UsuarioSemPermissaoParaAcaoException;
import com.usuario.quero_ler.mappers.DocumentoMapper;
import com.usuario.quero_ler.models.Documento;
import com.usuario.quero_ler.models.User;
import com.usuario.quero_ler.repository.DocumentoRepository;
import com.usuario.quero_ler.service.DocumentoServiceI;
import com.usuario.quero_ler.service.LoginServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DocumentoServiceImpl implements DocumentoServiceI {
    private final LoginServiceI login;
    private final DocumentoRepository repository;
    private final DocumentoMapper mapper;

    @Override
    public DocumentoResponseDto criar(DocumentoRequestDto dto) {
        validarUsuario();
        Documento documento = mapper.toEntity(dto);
        documento = repository.save(documento);
        return mapper.toResponse(documento);
    }

    @Override
    public void alterar(Long id, DocumentoAlteracoesDto dto) {
        validarUsuario();
        Documento documento = repository.findById(id).orElseThrow(
                ()-> new DocumentoNaoEncontradoException("Documento não cadastrado.")
        );

        documento = mapper.toUpdate(documento,dto);
        documento = repository.save(documento);
    }

    @Override
    public DocumentoResponseDto getTermosGeraisDeUso() {
        return null;
    }

    protected void validarUsuario() {
        User user = login.validarLogin();
        if (!user.getProfile().equals(UsuarioProfile.ADMINISTRADOR)) {
            throw new UsuarioSemPermissaoParaAcaoException("Você não tem permissão para realizar essa ação.");
        }
    }
}
