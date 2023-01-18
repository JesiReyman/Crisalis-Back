package com.crisalis.crisalisback.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="tipo",
        discriminatorType = DiscriminatorType.STRING)
@Entity
public abstract class Cliente{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long id;

    /*@Column(name = "tipo", insertable = false, updatable = false)
    private String tipo;*/

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> listaDePedidos = new ArrayList<Pedido>();

    public void addPedido(Pedido pedido){
        listaDePedidos.add(pedido);
        pedido.setCliente(this);
    }
}
