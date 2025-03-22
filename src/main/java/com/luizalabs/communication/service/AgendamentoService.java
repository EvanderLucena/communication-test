package com.luizalabs.communication.service;

import com.luizalabs.communication.dto.AgendamentoRequestDTO;
import com.luizalabs.communication.dto.AgendamentoResponseDTO;
import com.luizalabs.communication.mapper.AgendamentoMapper;
import com.luizalabs.communication.model.AgendamentoComunicacao;
import com.luizalabs.communication.model.StatusComunicacao;
import com.luizalabs.communication.repository.AgendamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    //pode ser usado em proximas sprints
    public void atualizarStatusEnviado(Long id) {
        AgendamentoComunicacao agendamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
        agendamento.setStatus(StatusComunicacao.ENVIADO);
        agendamento.setEnviadoEm(LocalDateTime.now());
        repository.save(agendamento);
    }

    public List<AgendamentoResponseDTO> listar(StatusComunicacao status) {
        List<AgendamentoComunicacao> agendamentos =
                (status == null) ? repository.findAll() : repository.findByStatus(status);

        return agendamentos.stream()
                .map(AgendamentoMapper::toDTO)
                .toList();
    }

}