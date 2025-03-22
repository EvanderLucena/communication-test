package com.luizalabs.communication.dto;

import com.luizalabs.communication.model.StatusComunicacao;
import com.luizalabs.communication.model.TipoComunicacao;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoResponseDTO {

    private Long id;
    private LocalDateTime dataHoraEnvio;
    private String destinatario;
    private String mensagem;
    private TipoComunicacao tipo;
    private StatusComunicacao status;
    private LocalDateTime criadoEm;
}