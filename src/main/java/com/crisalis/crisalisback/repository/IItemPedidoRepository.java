package com.crisalis.crisalisback.repository;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.model.ItemPedido;
import com.crisalis.crisalisback.model.Producto;
import com.crisalis.crisalisback.model.ProductoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByTipo(String tipo);
    List<ItemPedido> findByPedidoId(long idPedido);

    List<ItemPedido> findByPedidoIdAndTipo(long idPedido, String tipo);

    @Procedure
    Optional<ItemPedido> BUSCAR_SERVICIO_ACTIVO(long cliente_id);
}
