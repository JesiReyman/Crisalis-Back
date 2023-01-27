package com.crisalis.crisalisback.repository;

import com.crisalis.crisalisback.model.Producto;
import com.crisalis.crisalisback.model.ProductoBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductoBaseRepository extends JpaRepository<ProductoBase, Long> {
    List<ProductoBase> findByTipo(String tipo);

    Optional<ProductoBase> findByNombre(String nombre);
}
