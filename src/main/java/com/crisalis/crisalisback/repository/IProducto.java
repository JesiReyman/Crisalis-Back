package com.crisalis.crisalisback.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.Producto;

@Repository
public interface IProducto extends JpaRepository<Producto, Long>{
    Optional<Producto> findByNombre(String nombre);
    void deleteByNombre(String nombre);
}
