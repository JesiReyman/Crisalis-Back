package com.crisalis.crisalisback.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo", 
  discriminatorType = DiscriminatorType.STRING)
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double precioFinalUnitario;

    private int cantidad;

    @Column(name = "tipo", insertable = false, updatable = false)
    private String tipo;
 
    @ManyToOne
    @JsonIgnore
    private ProductoBase productoBase;

    @ManyToOne
    @JsonIgnore
    private Pedido pedido;

    public ItemPedido(double precioFinalUnitario, int cantidad, Pedido pedido) {
        this.precioFinalUnitario = precioFinalUnitario;
        this.cantidad = cantidad;
        this.pedido = pedido;
    }
}
