package com.luizalabs.communication.model;

import com.luizalabs.communication.enums.TipoComunicacaoEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "destinatarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destinatario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contato;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoComunicacaoEnum tipo;

    @ManyToOne
    @JoinColumn(name = "agendamento_id", nullable = false)
    private Agendamento agendamento;
}
