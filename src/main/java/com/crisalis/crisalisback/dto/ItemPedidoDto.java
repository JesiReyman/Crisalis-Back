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
    private BigDecimal totalAdicionales;
    private int aniosDeGarantia;
    private boolean activo;
    private String tipo;
    private BigDecimal descuento;

    @Builder
    public ItemPedidoDto(ItemPedido itemPedido) {
        this.nombre = itemPedido.getProductoBase().getNombre();
        this.cantidad = itemPedido.getCantidad();
        this.precioBase = itemPedido.getPrecioBase();
        this.totalImpuestos = itemPedido.getTotalImpuestos();
        this.precioFinalUnitario = itemPedido.getPrecioFinalUnitario();
        this.totalAdicionales = itemPedido.getTotalAdicionales();
        this.tipo = itemPedido.getTipo();


    }

    public ItemPedidoDto(ProductoPedido productoPedido){
        this.nombre = productoPedido.getProductoBase().getNombre();
        this.cantidad = productoPedido.getCantidad();
        this.precioBase = productoPedido.getPrecioBase();
        this.totalImpuestos = productoPedido.getTotalImpuestos();
        this.precioFinalUnitario = productoPedido.getPrecioFinalUnitario();
        this.totalAdicionales = productoPedido.getTotalAdicionales();
        this.tipo = productoPedido.getTipo();
        this.aniosDeGarantia = productoPedido.getAniosDeGarantia();
    }

    public ItemPedidoDto(ServicioPedido servicioPedido){
        this.nombre = servicioPedido.getProductoBase().getNombre();
        this.cantidad = servicioPedido.getCantidad();
        this.precioBase = servicioPedido.getPrecioBase();
        this.totalImpuestos = servicioPedido.getTotalImpuestos();
        this.precioFinalUnitario = servicioPedido.getPrecioFinalUnitario();
        this.totalAdicionales = servicioPedido.getTotalAdicionales();
        this.tipo = servicioPedido.getTipo();
        this.activo = servicioPedido.isActivo();
    }

    public static ItemPedido dtoAProductoPedido(ItemPedidoDto itemPedidoDto){
        return ProductoPedido.builder()
                .precioBase(itemPedidoDto.precioBase)
                .totalImpuestos(itemPedidoDto.totalImpuestos)
                .precioFinalUnitario(itemPedidoDto.precioFinalUnitario)
                .totalAdicionales(itemPedidoDto.totalAdicionales)
                .cantidad(itemPedidoDto.cantidad)
                .aniosDeGarantia(itemPedidoDto.aniosDeGarantia)
                .tipo(itemPedidoDto.tipo)
                .build();
    }

    public static ItemPedido dtoAServicioPedido(ItemPedidoDto itemPedidoDto){
        //aca en relidad deberia ir a buscar el producto base para setear el precioBase
        return ServicioPedido.builder()
                .precioBase(itemPedidoDto.precioBase)
                .totalImpuestos(itemPedidoDto.totalImpuestos)
                .precioFinalUnitario(itemPedidoDto.precioFinalUnitario)
                .totalAdicionales(itemPedidoDto.totalAdicionales)
                .cantidad(itemPedidoDto.cantidad)
                .activo(itemPedidoDto.activo)
                .tipo(itemPedidoDto.tipo)
                .build();
    }
}
