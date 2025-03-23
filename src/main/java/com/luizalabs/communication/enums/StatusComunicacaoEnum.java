package com.luizalabs.communication.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status possíveis para um agendamento")
public enum StatusComunicacaoEnum {
    PENDENTE,
    ENVIADO,
    FALHA
}