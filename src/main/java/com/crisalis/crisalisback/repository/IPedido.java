package com.crisalis.crisalisback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crisalis.crisalisback.model.Pedido;

public interface IPedido extends JpaRepository<Pedido, Long>{
    
}
