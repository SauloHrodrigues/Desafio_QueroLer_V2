package models;

import com.usuario.quero_ler.enuns.UsuarioProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String user;
    @Size(min = 8)
    @Column(name = "senha", nullable = false)
    private String senha;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile", nullable = false)
    private UsuarioProfile profile;
}