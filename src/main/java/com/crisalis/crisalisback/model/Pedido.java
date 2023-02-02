package com.crisalis.crisalisback.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.crisalis.crisalisback.enums.EstadoDePedido;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date fechaCreacion;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoDePedido estado ;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> listaDeItems = new ArrayList<ItemPedido>();

    @ManyToOne
    @JsonIgnore
    @NotNull
    private Cliente cliente;

    @OneToOne(mappedBy="pedido", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Descuento descuento;


    public Pedido(Cliente cliente) {
        this.fechaCreacion = new Date();
        this.estado = EstadoDePedido.PENDIENTE;
        this.cliente = cliente;
    }

    @Builder
    public Pedido( EstadoDePedido estado) {
        this.estado = estado;
    }


}
