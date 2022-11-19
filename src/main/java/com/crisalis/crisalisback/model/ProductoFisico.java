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
public class ProductoFisico extends Producto{
   /*  @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;*/

    private int aniosDeGarantia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pedido_id", nullable=false)
    private Pedido pedido;

    public ProductoFisico(long idProducto, String nombre, double precioBase, int aniosDeGarantia, Pedido pedido) {
        super(idProducto, nombre, precioBase);
        this.aniosDeGarantia = aniosDeGarantia;
        this.pedido = pedido;
    }
    
}





