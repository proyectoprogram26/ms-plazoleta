package com.proyectointegrador.msplazoleta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import com.proyectointegrador.msplazoleta.validation.NombreNoSoloNumeros;
import com.proyectointegrador.msplazoleta.validation.ValidSoloNumeros;

@Entity
@Data
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @NombreNoSoloNumeros(message = "El nombre no puede ser solo números")
    private String nombre;

    @NotBlank(message = "El NIT es obligatorio")
    @ValidSoloNumeros(message = "El NIT solo puede contener números")
    private String nit;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 13, message = "El teléfono no puede superar los 13 caracteres")
    @ValidSoloNumeros(message = "El teléfono debe ser numérico, puede incluir +")
    private String telefono;

    private String urlLogo;

    @NotNull(message = "El id del propietario es obligatorio")
    private Long idPropietario;
}
