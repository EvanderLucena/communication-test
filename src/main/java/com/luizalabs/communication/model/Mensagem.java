package com.luizalabs.communication.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mensagens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conteudo", nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @OneToOne
    @JoinColumn(name = "agendamento_id", nullable = false)
    private Agendamento agendamento;
}
