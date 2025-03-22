package com.luizalabs.communication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luizalabs.communication.model.TipoComunicacao;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;


public record AgendamentoRequestDTO(
        @NotNull @Future(message = "A data/hora do envio deve ser no futuro")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dataHoraEnvio,

        @NotBlank @Email(message = "O destinatário deve ser um email válido")
        String destinatario,

        @NotBlank @Size(max = 1000, message = "A mensagem deve ter no máximo 1000 caracteres")
        String mensagem,

        @NotNull
        TipoComunicacao tipo
) {}

