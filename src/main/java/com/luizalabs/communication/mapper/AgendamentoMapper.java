package com.luizalabs.communication.mapper;

import com.luizalabs.communication.dto.*;
import com.luizalabs.communication.enums.StatusComunicacaoEnum;
import com.luizalabs.communication.model.Agendamento;
import com.luizalabs.communication.model.Destinatario;
import com.luizalabs.communication.model.Envio;
import com.luizalabs.communication.model.Mensagem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgendamentoMapper {

    public Agendamento toEntity(AgendamentoRequestDTO dto) {
        Agendamento agendamento = Agendamento.builder()
                .dataHoraEnvio(dto.dataHoraEnvio())
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
                .toList();
        agendamento.setDestinatarios(destinatarios);

        // Set envio
        Envio envio = Envio.builder()
                .status(StatusComunicacaoEnum.PENDENTE)
                .agendamento(agendamento)
                .build();
        agendamento.setEnvio(envio);

        return agendamento;
    }

    public AgendamentoResponseDTO toDTO(Agendamento entity) {
        MensagemDTO mensagemDTO = new MensagemDTO(entity.getMensagem().getConteudo());

        List<DestinatarioDTO> destinatariosDTO = entity.getDestinatarios().stream()
                .map(d -> new DestinatarioDTO(d.getContato(), d.getTipo()))
                .toList();

        Envio envio = entity.getEnvio();
        EnvioDTO envioDTO = new EnvioDTO(
                envio.getStatus(),
                envio.getEnviadoEm(),
                envio.getErroEnvio()
        );

        return new AgendamentoResponseDTO(
                entity.getId(),
                entity.getDataHoraEnvio(),
                entity.getCriadoEm(),
                mensagemDTO,
                destinatariosDTO,
                envioDTO
        );
    }
}
