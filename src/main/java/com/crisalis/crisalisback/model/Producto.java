package com.crisalis.crisalisback.model;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
//@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idProducto;

    private String nombre;
    private double precioBase;


    protected double impuestoIVA(){
        double IVA = 0.21;
        return IVA * precioBase;
    }
}
