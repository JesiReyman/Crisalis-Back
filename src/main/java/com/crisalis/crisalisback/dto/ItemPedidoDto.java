package com.crisalis.crisalisback.dto;

import com.crisalis.crisalisback.model.ItemPedido;
import com.crisalis.crisalisback.model.ProductoPedido;
import com.crisalis.crisalisback.model.ServicioPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDto {
    @NotEmpty
    private String nombre;
    private int cantidad;
    private BigDecimal precioBase;
    private BigDecimal totalImpuestos;
    private BigDecimal precioFinalUnitario;
    private int aniosDeGarantia;
    private boolean activo;

    @Builder
    public ItemPedidoDto(ItemPedido itemPedido) {
        this.nombre = itemPedido.getProductoBase().getNombre();
        this.cantidad = itemPedido.getCantidad();
        this.precioBase = itemPedido.getPrecioBase();
        this.precioFinalUnitario = itemPedido.getPrecioFinalUnitario();
    }

    public static ItemPedido dtoAProductoPedido(ItemPedidoDto itemPedidoDto){
        return ProductoPedido.builder()
                .precioBase(itemPedidoDto.precioBase)
                .totalImpuestos(itemPedidoDto.totalImpuestos)
                .precioFinalUnitario(itemPedidoDto.precioFinalUnitario)
                .cantidad(itemPedidoDto.cantidad)
                .aniosDeGarantia(itemPedidoDto.aniosDeGarantia)
                .build();
    }

    public static ItemPedido dtoAServicioPedido(ItemPedidoDto itemPedidoDto){
        //aca en relidad deberia ir a buscar el producto base para setear el precioBase
        return ServicioPedido.builder()
                .precioBase(itemPedidoDto.precioBase)
                .totalImpuestos(itemPedidoDto.totalImpuestos)
                .precioFinalUnitario(itemPedidoDto.precioFinalUnitario)
                .cantidad(itemPedidoDto.cantidad)
                .activo(itemPedidoDto.activo)
                .build();
    }
}
