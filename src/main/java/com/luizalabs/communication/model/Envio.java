package com.luizalabs.communication.model;

import com.luizalabs.communication.enums.StatusComunicacaoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "envios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Envio {

    @Id
    @Column(name = "agendamento_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusComunicacaoEnum status;

    @Column(name = "enviado_em")
    private LocalDateTime enviadoEm;

    @Column(name = "erro_envio")
    private String erroEnvio;
}


