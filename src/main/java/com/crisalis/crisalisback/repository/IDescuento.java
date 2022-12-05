package com.crisalis.crisalisback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.Descuento;

@Repository
public interface IDescuento extends JpaRepository<Descuento, Long>{
    
}
