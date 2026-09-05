package com.proyectointegrador.msplazoleta.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NombreNoSoloNumerosValidator implements ConstraintValidator<NombreNoSoloNumeros, String> {

    @Override
    public boolean isValid(String nombre, ConstraintValidatorContext context) {
        if (nombre == null || nombre.isBlank()) return true;
        return !nombre.matches("^[0-9]+$");
    }
}
