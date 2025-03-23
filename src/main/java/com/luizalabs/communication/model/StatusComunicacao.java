package com.luizalabs.communication.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status possíveis para um agendamento")
public enum StatusComunicacao {
    PENDENTE,
    ENVIADO,
    FALHA
}