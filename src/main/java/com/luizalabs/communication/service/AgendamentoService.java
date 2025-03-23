package com.luizalabs.communication.service;

import com.luizalabs.communication.dto.AgendamentoRequestDTO;
import com.luizalabs.communication.dto.AgendamentoResponseDTO;
import com.luizalabs.communication.enums.StatusComunicacaoEnum;
import com.luizalabs.communication.mapper.AgendamentoMapper;
import com.luizalabs.communication.model.Agendamento;
import com.luizalabs.communication.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final AgendamentoMapper mapper;

    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO dto) {
        Agendamento entity = mapper.toEntity(dto);
        Agendamento salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    public Optional<AgendamentoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    public boolean deletar(Long id) {
        return repository.findById(id)
                .map(agendamento -> {
                    repository.delete(agendamento);
                    return true;
                })
                .orElse(false);
    }

    public List<AgendamentoResponseDTO> listar(StatusComunicacaoEnum status) {
        List<Agendamento> agendamentos = (status != null)
                ? repository.findByStatus(status)
                : repository.findAll();

        return agendamentos.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}