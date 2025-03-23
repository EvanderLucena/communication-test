package com.luizalabs.communication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Representa a mensagem a ser enviada na comunicação.")
public record MensagemDTO(

        @NotBlank(message = "O conteúdo da mensagem não pode estar em branco")
        @Size(max = 1000, message = "A mensagem deve ter no máximo 1000 caracteres")
        @Schema(
                description = "Conteúdo textual da mensagem",
                example = "Olá, essa é uma mensagem de teste!"
        )
        String conteudo

) {}
