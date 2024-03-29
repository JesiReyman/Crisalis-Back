package com.crisalis.crisalisback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.ProductoPedido;

@Repository
public interface IProductoPedido extends JpaRepository<ProductoPedido, Long>{
    List<ProductoPedido> findByPedidoId(Long idPedido);
}
