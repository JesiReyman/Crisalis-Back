package com.crisalis.crisalisback.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@NoArgsConstructor(force = true)
@Entity
@Table(name="inventario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo",
        discriminatorType = DiscriminatorType.STRING)
public abstract class ProductoBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //Agrego tipo como columna para poder filtrar entre productos y servicios
    @Column(name = "tipo", insertable = false, updatable = false)
    private String tipo;

    @NotNull
    @Column(unique = true)
    private String nombre;
    private String descripcion;
    private double precioBase;

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "productoBase")
    private List<ItemPedido> listaDeItems = new ArrayList<ItemPedido>();

    public ProductoBase(String tipo, String nombre, String descripcion, double precioBase) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
    }

    public ProductoBase(String nombre, String descripcion, double precioBase) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
    }
}
