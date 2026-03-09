package com.usuario.quero_ler.mappers;

import com.usuario.quero_ler.dtos.UsuarioDadosComplementarRequest;
import com.usuario.quero_ler.dtos.UsuarioRequestDto;
import com.usuario.quero_ler.dtos.UsuarioResponseDto;
import com.usuario.quero_ler.models.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDto dto){
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setCpf(dto.cpf());
        usuario.setDataDeNascimento(dto.dataDeNascimento());
        usuario.setAceitarTermos(dto.checkTermo());
        return usuario;
    }

    public Usuario update(Usuario usuario, UsuarioDadosComplementarRequest dto){
        usuario.setCidade(dto.cidade());
        usuario.setEstado(dto.estado());
        usuario.setPais(dto.pais());
        return usuario;
    }

    public UsuarioResponseDto toResponse(Usuario usuario) {
        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getUser().getProfile(),
                usuario.getDataDeNascimento(),
                usuario.getAceitarTermos(),
                usuario.getCidade(),
                usuario.getEstado(),
                usuario.getPais(),
                usuario.getFoto()
        );
    }
}