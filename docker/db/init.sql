-- Tabela principal de agendamentos
CREATE TABLE IF NOT EXISTS agendamentos (
                                            id SERIAL PRIMARY KEY,
                                            data_hora_envio TIMESTAMP NOT NULL,
                                            criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de envios (1:1 com agendamento) usando o mesmo ID de agendamento
CREATE TABLE IF NOT EXISTS envios (
                                      agendamento_id BIGINT PRIMARY KEY,
                                      status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    enviado_em TIMESTAMP,
    erro_envio TEXT,
    CONSTRAINT fk_envio_agendamento FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id)
    );

-- Tabela de mensagens (1:1 com agendamento)
CREATE TABLE IF NOT EXISTS mensagens (
                                         id SERIAL PRIMARY KEY,
                                         conteudo TEXT NOT NULL,
                                         agendamento_id BIGINT NOT NULL UNIQUE,
                                         CONSTRAINT fk_mensagem_agendamento FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id)
    );

-- Tabela de destinatários (1:N com agendamento)
CREATE TABLE IF NOT EXISTS destinatarios (
                                             id SERIAL PRIMARY KEY,
                                             contato VARCHAR(255) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    agendamento_id BIGINT NOT NULL,
    CONSTRAINT fk_destinatario_agendamento FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id)
    );

-- Inserindo agendamentos
INSERT INTO agendamentos (data_hora_envio) VALUES
                                               ('2025-03-24 10:00:00'),
                                               ('2025-03-24 11:00:00'),
                                               ('2025-03-24 12:00:00');

-- Inserindo envios (agora usa agendamento_id como PRIMARY KEY)
INSERT INTO envios (agendamento_id, status, enviado_em, erro_envio) VALUES
                                                                        (1, 'PENDENTE', NULL, NULL),
                                                                        (2, 'ENVIADO', '2025-03-24 11:01:00', NULL),
                                                                        (3, 'FALHA', NULL, 'Erro de conexão');

-- Inserindo mensagens
INSERT INTO mensagens (conteudo, agendamento_id) VALUES
                                                     ('Mensagem para primeiro agendamento', 1),
                                                     ('Mensagem enviada com sucesso', 2),
                                                     ('Tentativa falhou', 3);

-- Inserindo destinatários
INSERT INTO destinatarios (contato, tipo, agendamento_id) VALUES
                                                              ('cliente1@magalu.com', 'EMAIL', 1),
                                                              ('11999998888', 'SMS', 1),
                                                              ('cliente2@magalu.com', 'EMAIL', 2),
                                                              ('cliente3@magalu.com', 'EMAIL', 3),
                                                              ('551199991122', 'WHATSAPP', 3);
