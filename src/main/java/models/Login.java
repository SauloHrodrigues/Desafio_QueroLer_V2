package models;

import com.usuario.quero_ler.enuns.UsuarioPerfil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_login")
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    @Size(min = 8)
    @Column(name = "senha", nullable = false)
    private String senha;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private UsuarioPerfil perfil;
}