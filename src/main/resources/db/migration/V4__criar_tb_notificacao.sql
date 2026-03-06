CREATE TABLE tb_notificacao (
                                id BIGSERIAL PRIMARY KEY,
                                mnotificacao TEXT NOT NULL,
                                data_criacao TIMESTAMP,
                                data_visualizacao TIMESTAMP,
                                usuario_id BIGINT NOT NULL,

                                CONSTRAINT fk_notificacao_usuario
                                    FOREIGN KEY (usuario_id)
                                        REFERENCES tb_usuario(id)
                                        ON DELETE CASCADE
);