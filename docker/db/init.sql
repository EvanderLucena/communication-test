CREATE TABLE IF NOT EXISTS agendamentos_comunicacao (
                                                        id SERIAL PRIMARY KEY,
                                                        data_hora_envio TIMESTAMP NOT NULL,
                                                        destinatario VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    enviado_em TIMESTAMP,
    erro_envio TEXT,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

INSERT INTO agendamentos_comunicacao (data_hora_envio, destinatario, mensagem, tipo, status)
VALUES
    ('2025-03-23 10:00:00', 'teste1@magalu.com', 'Mensagem de teste 1', 'EMAIL', 'PENDENTE'),
    ('2025-03-23 11:00:00', 'teste2@magalu.com', 'Mensagem de teste 2', 'SMS', 'PENDENTE');
