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

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Descuento {
    @Id
    private long id;

    private BigDecimal descuentoGenerado;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pedido")
    private Pedido pedido;

    @ManyToOne
    private ServicioPedido servicioPedido;

}
