package com.crisalis.crisalisback.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Descuento {
    @Id
    private long id;

    private BigDecimal descuentoGenerado;

    @JsonBackReference
    @OneToOne
    @MapsId
    @JoinColumn(name = "pedido")
    private Pedido pedido;

    @ManyToOne
    private ServicioPedido servicioPedido;

}
