package com.luizalabs.communication.dto;

import com.luizalabs.communication.model.StatusComunicacao;
import com.luizalabs.communication.model.TipoComunicacao;
import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        LocalDateTime dataHoraEnvio,
        String destinatario,
        String mensagem,
        TipoComunicacao tipo,
        StatusComunicacao status,
        LocalDateTime enviadoEm,
        String erroEnvio,
        LocalDateTime criadoEm
) {}
