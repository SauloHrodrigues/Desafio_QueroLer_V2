package com.usuario.quero_ler.service.implementacoes;

import com.usuario.quero_ler.dtos.*;
import com.usuario.quero_ler.enuns.UsuarioProfile;
import com.usuario.quero_ler.exceptions.especies.UsuarioNaoEncontradoException;
import com.usuario.quero_ler.exceptions.especies.UsuarioSemPermissaoParaAcaoException;
import com.usuario.quero_ler.mappers.UsuarioMapper;
import com.usuario.quero_ler.models.User;
import com.usuario.quero_ler.models.Usuario;
import com.usuario.quero_ler.repository.UserRepository;
import com.usuario.quero_ler.repository.UsuarioNotificacaoRepository;
import com.usuario.quero_ler.repository.UsuarioRepository;
import com.usuario.quero_ler.service.LoginServiceI;
import com.usuario.quero_ler.service.UsuarioServiceI;
import com.usuario.quero_ler.utils.Senhas;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioServiceI {
    private final LoginServiceI login;
    private final UsuarioRepository repository;
    private final UserRepository userRepository;
    private final UsuarioMapper mapper;

    @Transactional
    @Override
    public UsuarioResponseDto criar(UsuarioRequestDto dto) {
        Senhas.validarIguais(dto.senha(), dto.confirmarSenha());
        User user = login.criar(dto, UsuarioProfile.LEITOR);
        Usuario usuario = mapper.toEntity(dto);
        usuario.setUser(user);
        usuario = repository.save(usuario);
        return mapper.toResponse(usuario);
    }

    @Override
    public void adicionarDados(Long id, UsuarioDadosComplementarRequest dto) {
        Usuario usuario = getUsuario(id);
        if (login.validarLogin(usuario.getUser())) {
            usuario = mapper.complementarCadastro(usuario, dto);
            repository.save(usuario);
        }
    }


    @Override
    public UsuarioDadosResponse getDadosDoUsuario(Long id) {
        Usuario usuario = getUsuario(id);
        login.validarLogin(usuario.getUser());
        return mapper.toResponseDados(usuario);
    }

    @Override
    public void atualizar(Long id, UsuarioAtualizadoLeitorReguest dto) {
        Usuario usuario = getUsuario(id);
        login.validarLogin(usuario.getUser());
        mapper.update(usuario, dto);
    }

    @Override
    public void atualizar(Long id, UsuarioAtualizadoAdministradorReguest dto) {
        Usuario usuario = getUsuario(id);
        login.validarLogin(usuario.getUser());
        mapper.update(usuario, dto);
    }

    @Override
    public void excluirPerfil(Long id) {
        Usuario usuario = getUsuario(id);
        login.validarLogin(usuario.getUser());
        if (usuario.getUser().getProfile().equals(UsuarioProfile.LEITOR)) {
            repository.delete(usuario);
        } else {
            throw new UsuarioSemPermissaoParaAcaoException("Ação não permitida para este usuário.");
        }
    }

    @Override
    public void alterarSenha(Long id, UsuarioAlterarSenhaReguest dto) {
        Senhas.validar(dto.senhaNova());
       User user = login.validarLogin();
        Senhas.validar(dto.senhaAtual(),user.getSenha());
        String novaSenha = Senhas.gerar(dto.senhaNova());
        user.setSenha(novaSenha);
        user= userRepository.save(user);
        // TODO
    }

    public Usuario getUsuario(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário" +
                        " com ID: '" + id + "'.")
        );
    }
}
