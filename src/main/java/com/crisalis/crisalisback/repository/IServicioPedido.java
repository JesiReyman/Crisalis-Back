package com.crisalis.crisalisback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.ServicioPedido;

@Repository
public interface IServicioPedido extends JpaRepository<ServicioPedido, Long>{
    public List<ServicioPedido> findByPedidoId(Long idPedido);
}
