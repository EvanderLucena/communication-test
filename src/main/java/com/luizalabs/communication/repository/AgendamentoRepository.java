package com.luizalabs.communication.repository;

import com.luizalabs.communication.model.AgendamentoComunicacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<AgendamentoComunicacao, Long> {
}