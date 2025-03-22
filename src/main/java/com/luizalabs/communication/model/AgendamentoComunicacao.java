package com.luizalabs.communication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos_comunicacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoComunicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data e hora do envio é obrigatória")
    @Column(name = "data_hora_envio", nullable = false)
    private LocalDateTime dataHoraEnvio;

    @NotBlank(message = "O destinatário é obrigatório")
    @Size(max = 255)
    @Column(nullable = false)
    private String destinatario;

    @NotBlank(message = "A mensagem é obrigatória")
    @Size(max = 1000)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @NotNull(message = "O tipo de comunicação é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoComunicacao tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusComunicacao status = StatusComunicacao.PENDENTE;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();
}
