package com.crisalis.crisalisback.dto;

import com.crisalis.crisalisback.enums.EstadoDePedido;
import com.crisalis.crisalisback.model.Pedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoDTO {
    private long id;
    private long dniOCuitCliente;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    private EstadoDePedido estado ;
    private BigDecimal precioBase;
    private BigDecimal totalImpuestos;
    private BigDecimal totalAdicionales;
    private BigDecimal total;
    private BigDecimal descuento;

    public PedidoDTO(Pedido pedido,
                     long dniOCuitCliente,
                     BigDecimal precioBase,
                     BigDecimal totalImpuestos,
                     BigDecimal totalAdicionales,
                     BigDecimal total,
                     BigDecimal descuento) {
        this.id = pedido.getId();
        this.fechaCreacion = pedido.getFechaCreacion();
        this.estado = pedido.getEstado();
        this.dniOCuitCliente = dniOCuitCliente;
        this.precioBase = precioBase;
        this.totalImpuestos = totalImpuestos;
        this.totalAdicionales = totalAdicionales;
        this.total = total;
        this.descuento = descuento;
    }

    public static Pedido dtoAPedido(PedidoDTO pedidoDTO){
        return Pedido.builder()
                .estado(pedidoDTO.estado)
                .build();
    }
}
