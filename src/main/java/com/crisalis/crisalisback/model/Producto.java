package com.crisalis.crisalisback.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter @Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo", 
  discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //Agrego tipo como columna para poder filtrar y obtener solo productos
    @Column(name = "tipo", insertable = false, updatable = false)
    private String tipo;

    final private String nombre;
    final private String descripcion;
    final private double precioBase;

    @Getter (value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "producto")
    private List<ItemPedido> listaDeItems = new ArrayList<ItemPedido>();


}
