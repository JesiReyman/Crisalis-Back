package com.crisalis.crisalisback.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor(force = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo",
        discriminatorType = DiscriminatorType.STRING)
public abstract class Cliente{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(name = "tipo", insertable = false, updatable = false)
    private String tipo;

    @Column(unique = true, nullable = false)
    private long dniOCuit;
    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> listaDePedidos = new ArrayList<Pedido>();

    public Cliente(String tipo, long dniOCuit) {
        this.tipo = tipo;
        this.dniOCuit = dniOCuit;
    }

    public void addPedido(Pedido pedido){
        listaDePedidos.add(pedido);
        pedido.setCliente(this);
    }
}
