package com.crisalis.crisalisback.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("servicio")
public class ServicioPedido extends ItemPedido{
    
    private boolean activo;

    public ServicioPedido(int cantidad, Producto producto, Pedido pedido) {
        super(cantidad, producto, pedido);
        this.activo = false;
    }


}
