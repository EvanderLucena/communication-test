package com.luizalabs.communication.mapper;

import com.luizalabs.communication.dto.*;
import com.luizalabs.communication.model.AgendamentoComunicacao;

public class AgendamentoMapper {

    public static AgendamentoComunicacao toEntity(AgendamentoRequestDTO dto) {
        return AgendamentoComunicacao.builder()
                .dataHoraEnvio(dto.getDataHoraEnvio())
                .destinatario(dto.getDestinatario())
                .mensagem(dto.getMensagem())
                .tipo(dto.getTipo())
                .build();
    }

    public static AgendamentoResponseDTO toDTO(AgendamentoComunicacao entity) {
        return AgendamentoResponseDTO.builder()
                .id(entity.getId())
                .dataHoraEnvio(entity.getDataHoraEnvio())
                .destinatario(entity.getDestinatario())
                .mensagem(entity.getMensagem())
                .tipo(entity.getTipo())
                .status(entity.getStatus())
                .criadoEm(entity.getCriadoEm())
                .build();
    }
}