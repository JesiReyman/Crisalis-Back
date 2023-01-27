package com.crisalis.crisalisback.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("producto")
public class ProductoPedido extends ItemPedido{

    private int aniosDeGarantia;
    @Builder
    public ProductoPedido(BigDecimal precioBase,
                          BigDecimal totalImpuestos,
                          BigDecimal precioFinalUnitario,
                          int cantidad, ProductoBase productoBase,
                          int aniosDeGarantia) {
        super(precioBase, totalImpuestos, precioFinalUnitario, cantidad, productoBase);
        this.aniosDeGarantia = aniosDeGarantia;
    }
}





