package com.proyectointegrador.msplazoleta.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SoloNumerosValidator implements ConstraintValidator<ValidSoloNumeros, String> {

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        if (valor == null || valor.isBlank()) return true;
        return valor.matches("^\\+?[0-9]+$");
    }
}
