package com.usuario.quero_ler.service.implementacoes;

import com.usuario.quero_ler.dtos.*;
import com.usuario.quero_ler.enuns.UsuarioProfile;
import com.usuario.quero_ler.exceptions.especies.UsuarioNaoEncontradoException;
import com.usuario.quero_ler.mappers.UsuarioMapper;
import com.usuario.quero_ler.models.User;
import com.usuario.quero_ler.models.Usuario;
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
    private final UsuarioMapper mapper;

    @Transactional
    @Override
    public UsuarioResponseDto criar(UsuarioRequestDto dto) {
        Senhas.validarIguais(dto.senha(),dto.confirmarSenha());
        User user = login.criar(dto, UsuarioProfile.LEITOR);
        Usuario usuario = mapper.toEntity(dto);
        usuario.setUser(user);
        usuario = repository.save(usuario);
        return mapper.toResponse(usuario);
    }

    @Override
    public void adicionarDados(Long id, UsuarioDadosComplementarRequest dto) {
        Usuario usuario = getUsuario(id);
        if(login.validarLogin(usuario.getUser())){
           usuario= mapper.update(usuario,dto);
            repository.save(usuario);
        }
    }

    protected Usuario getUsuario(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário" +
                        " com ID: '"+id+"'.")
        );
    }


    @Override
    public DadosDoUsuarioResponse getDadosDoUsuario(Long id) {
        return null;
    }

    @Override
    public void atualizarDados(Long id, DadosAtualizadosLeitorReguest dto) {

    }

    @Override
    public void atualizarDados(Long id, DadosAtualizadosAdministradorReguest dto) {

    }

    @Override
    public void excluirPerfil() {

    }

}
