package com.luizalabs.communication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Dados para criar um novo agendamento de comunicação.")
public record AgendamentoRequestDTO(

        @Schema(
                description = "Data e hora do agendamento da comunicação",
                example = "2025-03-24T10:30:00",
                type = "string",
                format = "date-time"
        )
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @NotNull(message = "A data e hora do envio é obrigatória")
        @Future(message = "A data e hora deve estar no futuro")
        LocalDateTime dataHoraEnvio,

        @NotNull(message = "A mensagem é obrigatória")
        @Valid
        @Schema(description = "Conteúdo da mensagem a ser enviada")
        MensagemDTO mensagem,

        @NotEmpty(message = "Deve haver ao menos um destinatário")
        @Valid
        @Schema(description = "Lista de destinatários da comunicação")
        List<DestinatarioDTO> destinatarios

) {}
