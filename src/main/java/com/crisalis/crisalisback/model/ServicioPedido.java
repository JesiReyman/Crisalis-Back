package com.crisalis.crisalisback.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter @Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("servicio")
public class ServicioPedido extends ItemPedido{
    
    private boolean activo;

    @Builder
    public ServicioPedido(BigDecimal precioBase,
                          BigDecimal totalImpuestos,
                          BigDecimal precioFinalUnitario,
                          BigDecimal totalAdicionales,
                          int cantidad,
                          String tipo,
                          ProductoBase productoBase,
                          boolean activo) {
        super(precioBase, totalImpuestos, precioFinalUnitario, cantidad, tipo, productoBase, totalAdicionales);
        this.activo = activo;
    }
}
