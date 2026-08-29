package com.proyectointegrador.msplazoleta.repository;

import com.proyectointegrador.msplazoleta.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {
}