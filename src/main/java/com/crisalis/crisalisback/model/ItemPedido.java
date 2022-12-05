package com.crisalis.crisalisback.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

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
 
    @ManyToOne
    @JsonIgnore
    private Producto producto;

    @ManyToOne
    @JsonIgnore
    private Pedido pedido;

    public ItemPedido(long id, int cantidad, Producto producto, Pedido pedido) {
      this.id = id;
      this.cantidad = cantidad;
      this.producto = producto;
      this.pedido = pedido;
    }
    
}
