package com.luizalabs.communication.mapper;

import com.luizalabs.communication.dto.*;
import com.luizalabs.communication.enums.StatusComunicacaoEnum;
import com.luizalabs.communication.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AgendamentoMapper {

    public Agendamento toEntity(AgendamentoRequestDTO dto) {
        Agendamento agendamento = Agendamento.builder()
                .dataHoraEnvio(dto.dataHoraEnvio())
                .status(StatusComunicacaoEnum.PENDENTE)
                .build();

        // Set mensagem
        Mensagem mensagem = Mensagem.builder()
                .conteudo(dto.mensagem().conteudo())
                .agendamento(agendamento)
                .build();
        agendamento.setMensagem(mensagem);

        // Set destinatarios
        List<Destinatario> destinatarios = dto.destinatarios().stream()
                .map(d -> Destinatario.builder()
                        .contato(d.contato())
                        .tipo(d.tipo())
                        .agendamento(agendamento)
                        .build())
                .collect(Collectors.toList());
        agendamento.setDestinatarios(destinatarios);

        return agendamento;
    }

    public AgendamentoResponseDTO toDTO(Agendamento entity) {
        MensagemDTO mensagemDTO = new MensagemDTO(entity.getMensagem().getConteudo());

        List<DestinatarioDTO> destinatariosDTO = entity.getDestinatarios().stream()
                .map(d -> new DestinatarioDTO(d.getContato(), d.getTipo()))
                .collect(Collectors.toList());

        return new AgendamentoResponseDTO(
                entity.getId(),
                entity.getDataHoraEnvio(),
                entity.getStatus(),
                entity.getEnviadoEm(),
                entity.getErroEnvio(),
                entity.getCriadoEm(),
                mensagemDTO,
                destinatariosDTO
        );
    }
}