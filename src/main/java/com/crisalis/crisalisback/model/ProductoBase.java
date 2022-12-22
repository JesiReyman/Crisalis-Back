package com.crisalis.crisalisback.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@RequiredArgsConstructor
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

    final private String nombre;
    final private String descripcion;
    final private double precioBase;

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "producto")
    private List<ItemPedido> listaDeItems = new ArrayList<ItemPedido>();
}
