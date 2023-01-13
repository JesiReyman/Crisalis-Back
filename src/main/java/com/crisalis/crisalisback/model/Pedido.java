package com.crisalis.crisalisback.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    private String estado;
     
    @OneToMany(mappedBy = "pedido", orphanRemoval = true)
    private List<ItemPedido> listaDeItems = new ArrayList<ItemPedido>();

    @ManyToOne
    @JsonIgnore
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
}
