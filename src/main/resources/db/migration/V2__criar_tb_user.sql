CREATE TABLE tb_user (
                          id BIGSERIAL PRIMARY KEY,
                          login VARCHAR(255),
                          senha VARCHAR(255) NOT NULL,
                          usuario_id BIGINT,
                          perfil VARCHAR(50) NOT NULL,

                          CONSTRAINT fk_login_usuario
                              FOREIGN KEY (usuario_id)
                                  REFERENCES tb_usuario(id)
                                  ON DELETE CASCADE
);