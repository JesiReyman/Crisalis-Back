package com.crisalis.crisalisback.repository;

import com.crisalis.crisalisback.model.Producto;
import com.crisalis.crisalisback.model.ProductoBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductoBaseRepository extends JpaRepository<ProductoBase, Long> {
    List<ProductoBase> findByTipo(String tipo);
}
