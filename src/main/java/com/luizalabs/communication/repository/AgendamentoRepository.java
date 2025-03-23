package com.luizalabs.communication.repository;

import com.luizalabs.communication.enums.StatusComunicacaoEnum;
import com.luizalabs.communication.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByStatus(StatusComunicacaoEnum status);
}