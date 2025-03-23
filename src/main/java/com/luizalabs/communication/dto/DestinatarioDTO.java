package com.luizalabs.communication.dto;

import com.luizalabs.communication.enums.TipoComunicacaoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Representa um destinatário da comunicação.")
public record DestinatarioDTO(

        @NotBlank(message = "O contato do destinatário não pode estar em branco")
        @Size(max = 255, message = "O contato deve ter no máximo 255 caracteres")
        @Schema(
                description = "Contato do destinatário. Pode ser email, número de telefone, etc.",
                example = "cliente@magalu.com"
        )
        String contato,

        @NotNull(message = "O tipo de comunicação é obrigatório")
        @Schema(
                description = "Tipo de canal a ser usado para a comunicação",
                example = "EMAIL"
        )
        TipoComunicacaoEnum tipo

) {}
