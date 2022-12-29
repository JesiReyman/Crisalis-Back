package com.crisalis.crisalisback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.Pedido;

@Repository
public interface IPedidoRepositorio extends JpaRepository<Pedido, Long>{
    List<Pedido> findByClienteId(Long personaId);

    List<Pedido> findByClienteIdOrderByFechaCreacionAsc(Long personaId);
}
