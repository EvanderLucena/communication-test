package com.luizalabs.communication.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AgendamentoResponseDTO(
        Long id,
        LocalDateTime dataHoraEnvio,
        LocalDateTime criadoEm,
        MensagemDTO mensagem,
        List<DestinatarioDTO> destinatarios,
        EnvioDTO envio
) {
}
