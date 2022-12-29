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

import com.crisalis.crisalisback.dto.ProductoDTO;
import lombok.*;


@Getter @Setter
//@RequiredArgsConstructor
//@NoArgsConstructor(force = true)
@NoArgsConstructor
@Entity

//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
/*@DiscriminatorColumn(name="tipo",
  discriminatorType = DiscriminatorType.STRING)*/
@DiscriminatorValue("producto")
public class Producto extends ProductoBase{

    /*@Id
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
    private List<ItemPedido> listaDeItems = new ArrayList<ItemPedido>();*/
    private int stock;

    /*@Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "producto")
    private List<ProductoPedido> listaProductosPedidos = new ArrayList<ProductoPedido>();*/


    public Producto(String tipo, String nombre, String descripcion, double precioBase, int stock) {
        super(tipo, nombre, descripcion, precioBase);
        this.stock = stock;
    }

    public Producto(String nombre, String descripcion, double precioBase, int stock) {
        super(nombre, descripcion, precioBase);
        this.stock = stock;
    }

    public Producto(ProductoDTO productoDTO){
        super(productoDTO.getNombre(), productoDTO.getDescripcion(), productoDTO.getPrecioBase());
        this.stock = productoDTO.getStock();
    }

    public ProductoDTO productoAproductoDTO(){
        return ProductoDTO.builder()
                .nombre(this.getNombre())
                .descripcion(this.getDescripcion())
                .stock(this.stock)
                .precioBase(this.getPrecioBase())
                .build();
    }


}
