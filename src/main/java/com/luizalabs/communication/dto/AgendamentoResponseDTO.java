package com.luizalabs.communication.dto;

import com.luizalabs.communication.model.StatusComunicacao;
import com.luizalabs.communication.model.TipoComunicacao;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Resposta com os dados de um agendamento de comunicação")
public record AgendamentoResponseDTO(

        @Schema(description = "Identificador do agendamento", example = "1")
        Long id,

        @Schema(description = "Data e hora programadas para o envio", example = "2025-03-23T10:00:00")
        LocalDateTime dataHoraEnvio,

        @Schema(description = "Destinatário da comunicação", example = "teste@magalu.com")
        String destinatario,

        @Schema(description = "Mensagem enviada", example = "Olá, sua entrega está a caminho!")
        String mensagem,

        @Schema(description = "Canal de comunicação", example = "EMAIL")
        TipoComunicacao tipo,

        @Schema(description = "Status atual do agendamento", example = "PENDENTE")
        StatusComunicacao status,

        @Schema(description = "Data e hora em que foi enviado", example = "2025-03-23T10:01:00")
        LocalDateTime enviadoEm,

        @Schema(description = "Mensagem de erro em caso de falha no envio", example = "Número de telefone inválido")
        String erroEnvio,

        @Schema(description = "Data de criação do agendamento", example = "2025-03-22T18:30:00")
        LocalDateTime criadoEm
) {}
