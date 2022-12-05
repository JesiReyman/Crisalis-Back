package com.crisalis.crisalisback.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Descuento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double descuentoGenerado;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    private ServicioPedido servicioPedido;

}
