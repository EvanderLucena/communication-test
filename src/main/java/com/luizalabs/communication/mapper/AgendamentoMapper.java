package com.luizalabs.communication.mapper;

import com.luizalabs.communication.dto.*;
import com.luizalabs.communication.model.AgendamentoComunicacao;

public class AgendamentoMapper {

    public static AgendamentoComunicacao toEntity(AgendamentoRequestDTO dto) {
        return AgendamentoComunicacao.builder()
                .dataHoraEnvio(dto.dataHoraEnvio())
                .destinatario(dto.destinatario())
                .mensagem(dto.mensagem())
                .tipo(dto.tipo())
                .build();
    }

    public static AgendamentoResponseDTO toDTO(AgendamentoComunicacao entity) {
        return new AgendamentoResponseDTO(
                entity.getId(),
                entity.getDataHoraEnvio(),
                entity.getDestinatario(),
                entity.getMensagem(),
                entity.getTipo(),
                entity.getStatus(),
                entity.getCriadoEm()
        );
    }
}