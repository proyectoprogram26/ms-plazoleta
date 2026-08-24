package com.proyectointegrador.msplazoleta.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NombreNoSoloNumerosValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NombreNoSoloNumeros {
    String message() default "El nombre no puede ser solo números";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
