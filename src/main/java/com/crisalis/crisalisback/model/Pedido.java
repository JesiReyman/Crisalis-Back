package com.crisalis.crisalisback.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

@Data
@NoArgsConstructor
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date fechaCreacion;

    private String estado;


    @OneToMany(mappedBy = "pedido", orphanRemoval = true)
    private List<ItemPedido> listaDeItems = new ArrayList<ItemPedido>();

    @ManyToOne
    @JsonIgnore
    //@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Cliente cliente;

    /*@ManyToOne
    @JsonIgnore
    private EmpresaCliente empresaCliente;*/

    public Pedido(Cliente cliente) {
        this.fechaCreacion = new Date();
        this.estado = "Pendiente";
        this.cliente = cliente;
    }

    /*public Pedido(EmpresaCliente empresa) {
        this.fechaCreacion = new Date();
        this.estado = "Pendiente";
        this.empresaCliente = empresa;
    }*/

    public void addItemPedido(ItemPedido itemPedido){
        listaDeItems.add(itemPedido);
        itemPedido.setPedido(this);
    }
}
