package com.luizalabs.communication.service;

import com.luizalabs.communication.dto.AgendamentoRequestDTO;
import com.luizalabs.communication.dto.AgendamentoResponseDTO;
import com.luizalabs.communication.mapper.AgendamentoMapper;
import com.luizalabs.communication.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository repository;

    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO dto) {
        var entity = AgendamentoMapper.toEntity(dto);
        return AgendamentoMapper.toDTO(repository.save(entity));
    }

    public Optional<AgendamentoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id).map(AgendamentoMapper::toDTO);
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}