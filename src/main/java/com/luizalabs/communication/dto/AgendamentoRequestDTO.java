package com.luizalabs.communication.dto;

import com.luizalabs.communication.model.TipoComunicacao;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;


public record AgendamentoRequestDTO(
        @NotNull LocalDateTime dataHoraEnvio,
        @NotBlank String destinatario,
        @NotBlank String mensagem,
        @NotNull TipoComunicacao tipo
) {}
