package com.luizalabs.communication.configs;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ContatoValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContatoValido {

    String message() default "Contato inválido para o tipo de comunicação";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
