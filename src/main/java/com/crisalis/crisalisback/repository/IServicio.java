package com.crisalis.crisalisback.repository;

import com.crisalis.crisalisback.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.Servicio;

import java.util.Optional;

@Repository
public interface IServicio extends JpaRepository<Servicio, Long>{
    Optional<Servicio> findByNombre(String nombre);
    void deleteByNombre(String nombre);
}
