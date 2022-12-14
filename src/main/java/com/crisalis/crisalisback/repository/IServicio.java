package com.crisalis.crisalisback.repository;

import com.crisalis.crisalisback.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.Servicio;

@Repository
public interface IServicio extends JpaRepository<Servicio, Long>{
    public Servicio findByNombre(String nombre);
    public void deleteByNombre(String nombre);
}
