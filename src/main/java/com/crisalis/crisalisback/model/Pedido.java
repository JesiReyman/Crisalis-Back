package com.crisalis.crisalisback.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.crisalis.crisalisback.enums.EstadoDePedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime fechaCreacion;
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
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoDePedido.PENDIENTE;
        this.cliente = cliente;
    }

    @Builder
    public Pedido( EstadoDePedido estado) {
        this.estado = estado;
    }


}
