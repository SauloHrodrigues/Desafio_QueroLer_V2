package com.usuario.quero_ler.fixtures;

import com.usuario.quero_ler.dtos.usuario.UsuarioRequestDto;
import com.usuario.quero_ler.enuns.UsuarioProfile;
import com.usuario.quero_ler.models.User;
import com.usuario.quero_ler.models.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;

public class UserFixture {
    private static final Long ID = 1L;
    private static final String NOME = "Nome SobreNome";
    private static final String EMAIL = "nome@gmail.com";
    private static final String CONFIRMAR_EMAIL = "nome@gmail.com";
    private static final String SENHA = "Teste123&";
    private static final String CONFIRMAR_SENHA = "Teste123&";
    private static final String CPF = "49618203000";
    private static final LocalDate DATA_DE_NASCIMENTO = LocalDate.of(2000,12,05);
    private static final Boolean CHECK_TERMO = true;
    private static final String CIDADE= "Valinhos";
    private static final String ESTADO = "São paulo";
    private static final String PAIS = "Brasil";
    private static final byte[] FOTO = null;


    public static UsuarioRequestDto  requestDto(){
        return new UsuarioRequestDto(
                NOME,EMAIL,CONFIRMAR_EMAIL,SENHA,CONFIRMAR_SENHA,CPF,DATA_DE_NASCIMENTO,CHECK_TERMO
        );
    }

    public static User userEntity(UsuarioProfile profile){
        String senhaHash = BCrypt.hashpw(SENHA, BCrypt.gensalt());
        return new User(2L,EMAIL,senhaHash,profile,null);
    }

    public static Usuario entidadePrincipal(User user){
        Usuario usuario = new Usuario();
        usuario.setId(ID);
        usuario.setNome(NOME);
        usuario.setEmail(EMAIL);
        usuario.setCpf(CPF);
        usuario.setDataDeNascimento(DATA_DE_NASCIMENTO);
        usuario.setAceitarTermos(CHECK_TERMO);
        usuario.setUser(user);
        return usuario;
    }

    public static Usuario entidadeCompleta(User user){
        Usuario usuario = entidadePrincipal(user);
        usuario.setCidade(CIDADE);
        usuario.setEstado(ESTADO);
        usuario.setPais(PAIS);
        usuario.setFoto(FOTO);
        return usuario;
    }
}