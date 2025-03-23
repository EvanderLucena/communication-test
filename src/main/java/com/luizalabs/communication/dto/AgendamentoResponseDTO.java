package com.luizalabs.communication.dto;

import com.luizalabs.communication.enums.StatusComunicacaoEnum;
import java.time.LocalDateTime;
import java.util.List;

public record AgendamentoResponseDTO(
        Long id,
        LocalDateTime dataHoraEnvio,
        StatusComunicacaoEnum status,
        LocalDateTime enviadoEm,
        String erroEnvio,
        LocalDateTime criadoEm,
        MensagemDTO mensagem,
        List<DestinatarioDTO> destinatarios
) {}
