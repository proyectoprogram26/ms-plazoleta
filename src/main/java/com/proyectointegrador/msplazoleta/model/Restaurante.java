package com.proyectointegrador.msplazoleta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.proyectointegrador.msplazoleta.validation.NombreNoSoloNumeros;
import com.proyectointegrador.msplazoleta.validation.ValidSoloNumeros;

@Entity
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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getUrlLogo() { return urlLogo; }
    public void setUrlLogo(String urlLogo) { this.urlLogo = urlLogo; }
    public Long getIdPropietario() { return idPropietario; }
    public void setIdPropietario(Long idPropietario) { this.idPropietario = idPropietario; }
}
