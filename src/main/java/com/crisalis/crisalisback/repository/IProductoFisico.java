package com.crisalis.crisalisback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.crisalis.crisalisback.model.ProductoFisico;

@Repository
public interface IProductoFisico extends JpaRepository<ProductoFisico, Long>{
    
}
