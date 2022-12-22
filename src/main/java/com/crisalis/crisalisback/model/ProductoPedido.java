package com.crisalis.crisalisback.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("producto")
public class ProductoPedido extends ItemPedido{

    private int aniosDeGarantia;

    public ProductoPedido(int cantidad, Producto producto, Pedido pedido, int aniosDeGarantia) {
        super(cantidad, producto, pedido);
        this.aniosDeGarantia = aniosDeGarantia;
    }
}





