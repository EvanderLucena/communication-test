package com.luizalabs.communication.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de comunicação disponíveis")
public enum TipoComunicacaoEnum {
    EMAIL,
    SMS,
    WHATSAPP,
    PUSH
}
