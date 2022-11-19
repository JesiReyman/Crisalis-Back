package com.crisalis.crisalisback.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date fechaCreacion;

    @OneToMany(mappedBy = "pedido")
    private List<ProductoFisico> listaDeProductos = new ArrayList<ProductoFisico>();
    
    @OneToMany(mappedBy = "pedido")
    private List<Servicio> listaDeServicios = new ArrayList<Servicio>();
}
