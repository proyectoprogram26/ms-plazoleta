package com.proyectointegrador.msplazoleta.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ModificarPlatoRequest {

    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 1, message = "El precio debe ser un entero positivo mayor a 0")
    private Integer precio;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    public Integer getPrecio() { return precio; }
    public void setPrecio(Integer precio) { this.precio = precio; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}