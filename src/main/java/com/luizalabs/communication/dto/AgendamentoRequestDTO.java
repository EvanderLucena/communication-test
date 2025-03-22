package com.luizalabs.communication.dto;

import com.luizalabs.communication.model.TipoComunicacao;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoRequestDTO {
    @NotNull
    private LocalDateTime dataHoraEnvio;

    @NotBlank
    private String destinatario;

    @NotBlank
    private String mensagem;

    @NotNull
    private TipoComunicacao tipo;
}
