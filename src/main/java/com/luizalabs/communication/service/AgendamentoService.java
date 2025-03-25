package com.luizalabs.communication.service;

import com.luizalabs.communication.dto.AgendamentoRequestDTO;
import com.luizalabs.communication.dto.AgendamentoResponseDTO;
import com.luizalabs.communication.enums.StatusComunicacaoEnum;
import com.luizalabs.communication.exception.BusinessException;
import com.luizalabs.communication.mapper.AgendamentoMapper;
import com.luizalabs.communication.model.Agendamento;
import com.luizalabs.communication.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final AgendamentoMapper mapper;

    public AgendamentoResponseDTO criarAgendamento(final AgendamentoRequestDTO requestDTO) {
        Agendamento agendamento = mapper.toEntity(requestDTO);
        Agendamento salvo = repository.save(agendamento);
        return mapper.toDTO(salvo);
    }


    public Optional<AgendamentoResponseDTO> buscarPorId(final Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    public boolean deletar(final Long id) {
        return repository.findById(id)
                .map(agendamento -> {
                    if (agendamento.getEnvio() != null &&
                            agendamento.getEnvio().getStatus() ==
                            StatusComunicacaoEnum.ENVIADO) {
                        throw new BusinessException("Não é permitido excluir agendamentos que já foram enviados.");
                    }
                    repository.delete(agendamento);
                    return true;
                })
                .orElse(false);
    }

    public List<AgendamentoResponseDTO> listar(final StatusComunicacaoEnum status) {
        List<Agendamento> agendamentos = (status != null)
                ? repository.findByEnvio_Status(status)
                : repository.findAll();

        return agendamentos.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<String> buscarStatusPorId(final Long id) {
        return repository.findById(id)
                .map(agendamento -> {
                    if (agendamento.getEnvio() == null || agendamento.getEnvio().getStatus() == null) {
                        return "DESCONHECIDO";
                    }
                    return agendamento.getEnvio().getStatus().name();
                });
    }

}
