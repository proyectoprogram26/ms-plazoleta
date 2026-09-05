package com.proyectointegrador.msplazoleta.exception;

public class RestauranteNoEncontradoException extends RuntimeException {
    public RestauranteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}