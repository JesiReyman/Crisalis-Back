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
    private int cantidad;
    private double precioFinalUnitario;

    @Column(name = "tipo", insertable = false, updatable = false)
    private String tipo;
 
    @ManyToOne
    @JsonIgnore
    private Producto producto;

    @ManyToOne
    @JsonIgnore
    private Pedido pedido;

    public ItemPedido(int cantidad, Producto producto, Pedido pedido) {
      this.cantidad = cantidad;
      this.producto = producto;
      this.pedido = pedido;
    }
    
}
