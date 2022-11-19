package com.crisalis.crisalisback.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@Entity
public class Servicio extends Producto{
    
    private double precioSoporte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pedido_id", nullable=false)
    private Pedido pedido;

    

    public Servicio(long idProducto, String nombre, double precioBase, double precioSoporte, Pedido pedido) {
        super(idProducto, nombre, precioBase);
        this.precioSoporte = precioSoporte;
        this.pedido = pedido;
    }

    public double impuestoIIBB(){
        double IIBB = 0.035;
        return this.getPrecioBase() * IIBB;
    }

    public double precioTotal(){
        double precioConIva = this.getPrecioBase() + this.impuestoIVA();
        double precioConIIBB = this.getPrecioBase() + this.impuestoIIBB();
        double precioConImpuestos = precioConIva + precioConIIBB;
        double precioTotal = precioConImpuestos + this.precioSoporte;
        return precioTotal;
    }
}
