package com.crisalis.crisalisback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.Producto;

@Repository
public interface IProducto extends JpaRepository<Producto, Long>{
    public Producto findByNombre(String nombre);
    public void deleteByNombre(String nombre);
}
