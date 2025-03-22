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

-- Massa de dados para diferentes cenários
INSERT INTO agendamentos_comunicacao (data_hora_envio, destinatario, mensagem, tipo, status, enviado_em, erro_envio)
VALUES
    -- Agendamentos pendentes
    ('2025-03-23 10:00:00', 'teste1@magalu.com', 'Mensagem de teste 1', 'EMAIL', 'PENDENTE', null, null),
    ('2025-03-23 11:00:00', 'teste2@magalu.com', 'Mensagem de teste 2', 'SMS', 'PENDENTE', null, null),

    -- Agendamento enviado com sucesso
    ('2025-03-22 08:00:00', 'cliente@magalu.com', 'Mensagem enviada com sucesso', 'PUSH', 'ENVIADO', '2025-03-22 08:01:00', null),

    -- Agendamento com falha
    ('2025-03-21 14:00:00', 'telefone_invalido', 'Erro ao enviar SMS', 'SMS', 'FALHA', null, 'Número de telefone inválido'),

    -- Outro enviado
    ('2025-03-20 09:00:00', 'user@magalu.com', 'Outro envio realizado', 'WHATSAPP', 'ENVIADO', '2025-03-20 09:00:10', null);
