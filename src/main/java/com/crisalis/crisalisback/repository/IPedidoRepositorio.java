package com.crisalis.crisalisback.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.Pedido;

@Repository
public interface IPedidoRepositorio extends JpaRepository<Pedido, Long>{

    List<Pedido> findByClienteId(Long personaId);

    Page<Pedido> findByClienteDniOCuitOrderByFechaCreacionDesc(Long personaId, Pageable pageable);

    Page<Pedido> findAll(Pageable pageable);
}
