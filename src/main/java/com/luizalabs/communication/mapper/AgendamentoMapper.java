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

        Agendamento agendamento = new Agendamento();
        agendamento.setDataHoraEnvio(dto.dataHoraEnvio());

        Mensagem mensagem = new Mensagem();
        mensagem.setConteudo(dto.mensagem().conteudo());
        mensagem.setAgendamento(agendamento);
        agendamento.setMensagem(mensagem);

        List<Destinatario> destinatarios = dto.destinatarios().stream()
                .map(destinatarioDTO -> {
                    Destinatario d = new Destinatario();
                    d.setContato(destinatarioDTO.contato());
                    d.setTipo(destinatarioDTO.tipo());
                    d.setAgendamento(agendamento);
                    return d;
                }).toList();

        agendamento.setDestinatarios(destinatarios);

        Envio envio = new Envio();
        envio.setStatus(StatusComunicacaoEnum.PENDENTE);
        envio.setAgendamento(agendamento);
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
