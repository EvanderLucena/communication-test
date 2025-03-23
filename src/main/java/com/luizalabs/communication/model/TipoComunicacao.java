package com.luizalabs.communication.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de comunicação disponíveis")
public enum TipoComunicacao {
    EMAIL,
    SMS,
    WHATSAPP,
    PUSH
}
