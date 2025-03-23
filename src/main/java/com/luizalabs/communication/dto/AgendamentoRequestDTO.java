package com.luizalabs.communication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luizalabs.communication.model.TipoComunicacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Schema(description = "Requisição para agendamento de envio de comunicação")
public record AgendamentoRequestDTO(

        @NotNull
        @Future
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @Schema(description = "Data e hora para o envio da comunicação", example = "2025-03-23T10:00:00")
        LocalDateTime dataHoraEnvio,

        @NotBlank
        @Schema(description = "Destinatário da comunicação. Pode ser e-mail, telefone ou token", example = "teste@magalu.com")
        String destinatario,

        @NotBlank
        @Schema(description = "Mensagem que será entregue ao destinatário", example = "Olá, sua entrega está a caminho!")
        String mensagem,

        @NotNull
        @Schema(description = "Canal de comunicação a ser utilizado", example = "EMAIL", allowableValues = {"EMAIL", "SMS", "WHATSAPP", "PUSH"})
        TipoComunicacao tipo
) {}