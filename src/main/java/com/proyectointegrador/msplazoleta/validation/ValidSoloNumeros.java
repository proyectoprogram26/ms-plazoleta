package com.proyectointegrador.msplazoleta.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SoloNumerosValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSoloNumeros {
    String message() default "Solo se permiten números";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
