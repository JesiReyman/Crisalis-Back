package com.crisalis.crisalisback.dto;

import com.crisalis.crisalisback.enums.EstadoDePedido;
import com.crisalis.crisalisback.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoDTO {
    private long id;
    private long dniOCuitCliente;
    private Date fechaCreacion;
    private EstadoDePedido estado ;
    private BigDecimal precioBase;
    private BigDecimal totalImpuestos;
    private BigDecimal totalAdicionales;
    private BigDecimal total;

    public PedidoDTO(Pedido pedido, long dniOCuitCliente, BigDecimal precioBase, BigDecimal totalImpuestos, BigDecimal totalAdicionales  , BigDecimal total){
        this.id = pedido.getId();
        this.fechaCreacion = pedido.getFechaCreacion();
        this.estado = pedido.getEstado();
        this.dniOCuitCliente = dniOCuitCliente;
        this.precioBase = precioBase;
        this.totalImpuestos = totalImpuestos;
        this.totalAdicionales = totalAdicionales;
        this.total = total;
    }

    public static Pedido dtoAPedido(PedidoDTO pedidoDTO){
        return Pedido.builder()
                .estado(pedidoDTO.estado)
                .build();
    }
}
