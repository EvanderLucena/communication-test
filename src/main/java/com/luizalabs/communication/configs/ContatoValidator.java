package com.luizalabs.communication.configs;

import com.luizalabs.communication.dto.DestinatarioDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ContatoValidator implements ConstraintValidator<ContatoValido, DestinatarioDTO> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10,15}$");

    @Override
    public boolean isValid(DestinatarioDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.contato() == null || dto.tipo() == null) return true; // outros @NotNull já validam

        return switch (dto.tipo()) {
            case EMAIL -> EMAIL_PATTERN.matcher(dto.contato()).matches();
            case SMS, WHATSAPP -> PHONE_PATTERN.matcher(dto.contato()).matches();
            case PUSH -> true;
        };
    }
}
