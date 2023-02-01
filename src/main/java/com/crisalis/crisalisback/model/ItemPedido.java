package com.crisalis.crisalisback.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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

    private BigDecimal precioBase;
    private BigDecimal totalImpuestos;
    private BigDecimal totalAdicionales;
    private BigDecimal precioFinalUnitario;

    private int cantidad;

    @Column(name = "tipo", insertable = false, updatable = false)
    private String tipo;
 
    @ManyToOne
    @JsonIgnore
    private ProductoBase productoBase;

    @ManyToOne
    @JsonIgnore
    private Pedido pedido;

    public ItemPedido(BigDecimal precioBase, BigDecimal totalImpuestos, BigDecimal precioFinalUnitario, int cantidad, ProductoBase productoBase, BigDecimal totalAdicionales) {
        this.precioBase = precioBase;
        this.totalImpuestos = totalImpuestos;
        this.precioFinalUnitario = precioFinalUnitario;
        this.cantidad = cantidad;
        this.productoBase = productoBase;
        this.totalAdicionales = totalAdicionales;
    }
}
