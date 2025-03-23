package com.luizalabs.communication.service;

import com.luizalabs.communication.dto.AgendamentoRequestDTO;
import com.luizalabs.communication.dto.AgendamentoResponseDTO;
import com.luizalabs.communication.enums.StatusComunicacaoEnum;
import com.luizalabs.communication.exception.BusinessException;
import com.luizalabs.communication.mapper.AgendamentoMapper;
import com.luizalabs.communication.model.Agendamento;
import com.luizalabs.communication.model.Destinatario;
import com.luizalabs.communication.model.Envio;
import com.luizalabs.communication.model.Mensagem;
import com.luizalabs.communication.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final AgendamentoMapper mapper;

    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO requestDTO) {
        Agendamento agendamento = new Agendamento();
        agendamento.setDataHoraEnvio(requestDTO.dataHoraEnvio());

        Mensagem mensagem = new Mensagem();
        mensagem.setConteudo(requestDTO.mensagem().conteudo());
        mensagem.setAgendamento(agendamento);
        agendamento.setMensagem(mensagem);

        List<Destinatario> destinatarios = requestDTO.destinatarios().stream()
                .map(dto -> {
                    Destinatario d = new Destinatario();
                    d.setContato(dto.contato());
                    d.setTipo(dto.tipo());
                    d.setAgendamento(agendamento);
                    return d;
                })
                .collect(Collectors.toCollection(ArrayList::new));

        agendamento.setDestinatarios(destinatarios);

        Envio envio = new Envio();
        envio.setStatus(StatusComunicacaoEnum.PENDENTE);
        envio.setAgendamento(agendamento);
        agendamento.setEnvio(envio);

        Agendamento salvo = repository.save(agendamento);
        return mapper.toDTO(salvo);
    }


    public Optional<AgendamentoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    public boolean deletar(Long id) {
        return repository.findById(id)
                .map(agendamento -> {
                    if (agendamento.getEnvio() != null && agendamento.getEnvio().getStatus() == StatusComunicacaoEnum.ENVIADO) {
                        throw new BusinessException("Não é permitido excluir agendamentos que já foram enviados.");
                    }
                    repository.delete(agendamento);
                    return true;
                })
                .orElse(false);
    }

    public List<AgendamentoResponseDTO> listar(StatusComunicacaoEnum status) {
        List<Agendamento> agendamentos = (status != null)
                ? repository.findByEnvio_Status(status)
                : repository.findAll();

        return agendamentos.stream()
                .map(mapper::toDTO)
                .toList();
    }
}
