package com.usuario.quero_ler.service.implementacoes;

import com.usuario.quero_ler.dtos.LoginRequestDto;
import com.usuario.quero_ler.dtos.UsuarioRequestDto;
import com.usuario.quero_ler.enuns.UsuarioProfile;
import com.usuario.quero_ler.exceptions.especies.UsuarioComPerfilInvalidoException;
import com.usuario.quero_ler.exceptions.especies.UsuarioNaoAutenticadoException;
import com.usuario.quero_ler.repository.UserRepository;
import com.usuario.quero_ler.service.LoginServiceI;
import com.usuario.quero_ler.utils.Senhas;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.usuario.quero_ler.models.User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginServiceI {
    private User logado;
    private final UserRepository repository;

    @Transactional
    @Override
    public User criar(UsuarioRequestDto dto, UsuarioProfile profile) {
        User user = new User();
        String senha = Senhas.gerar(dto.senha());
        user.setUser(dto.email());
        user.setSenha(senha);
        user.setProfile(profile);
        user = repository.save(user);
        return user;
    }

    @Override
    public void login(LoginRequestDto dto) {
        User user = repository.findByUserIgnoreCase(dto.user()).orElseThrow(
                () -> new RuntimeException("Usuario não cadastrado.")
        );

        if(!user.getProfile().equals(dto.profile())){
            throw new UsuarioComPerfilInvalidoException("Perfil inválido");
        }

        Boolean senhaValida = Senhas.validar(dto.senha(), user.getSenha());

        if (senhaValida) {
            logado = user;
        } else {
            throw new RuntimeException("Senha invalida.");
        }
    }

    @Override
    public User validarLogin(){
        if(logado != null){
            return logado;
        } else {
            throw new UsuarioNaoAutenticadoException("Usuario não logado!");
        }
    }
    @Override
    public Boolean validarLogin(User user) {
        if (logado!= null && user.getUser().equalsIgnoreCase(logado.getUser())) {
            return true;
        } else  {
            throw new UsuarioNaoAutenticadoException("Usuário não logado. ");
        }
    }
}