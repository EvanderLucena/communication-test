package com.luizalabs.communication.repository;

import com.luizalabs.communication.model.AgendamentoComunicacao;
import com.luizalabs.communication.model.StatusComunicacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<AgendamentoComunicacao, Long> {

    List<AgendamentoComunicacao> findByStatus(StatusComunicacao status);
}