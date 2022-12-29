package com.crisalis.crisalisback.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("servicio")
public class ServicioPedido extends ItemPedido{
    
    private boolean activo;

    /*@ManyToOne
    @JsonIgnore
    private Servicio servicio;*/

    public ServicioPedido(double precioFinalUnitario, int cantidad, Pedido pedido, boolean activo) {
        super(precioFinalUnitario, cantidad, pedido);
        this.activo = activo;
    }
}
