package com.crisalis.crisalisback.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /*@ManyToOne
    @JsonIgnore
    private Producto producto;*/

    public ProductoPedido(double precioBase, double impuestoIVA, double precioFinalUnitario, int cantidad, ProductoBase productoBase, int aniosDeGarantia) {
        super(precioBase, impuestoIVA, precioFinalUnitario, cantidad, productoBase);
        this.aniosDeGarantia = aniosDeGarantia;
    }
}





