package com.luizalabs.communication.dto;

import com.luizalabs.communication.enums.StatusComunicacaoEnum;

import java.time.LocalDateTime;

public record EnvioDTO(
        StatusComunicacaoEnum status,
        LocalDateTime enviadoEm,
        String erroEnvio
) {
}
