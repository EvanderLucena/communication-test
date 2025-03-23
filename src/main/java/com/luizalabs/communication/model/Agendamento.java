package com.luizalabs.communication.model;

import com.luizalabs.communication.enums.StatusComunicacaoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "agendamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_hora_envio", nullable = false)
    private LocalDateTime dataHoraEnvio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusComunicacaoEnum status = StatusComunicacaoEnum.PENDENTE;

    @Column(name = "enviado_em")
    private LocalDateTime enviadoEm;

    @Column(name = "erro_envio", columnDefinition = "TEXT")
    private String erroEnvio;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @OneToOne(mappedBy = "agendamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Mensagem mensagem;

    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Destinatario> destinatarios;
}
