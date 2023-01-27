package com.crisalis.crisalisback.repository;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.model.ItemPedido;
import com.crisalis.crisalisback.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByTipo(String tipo);
}
