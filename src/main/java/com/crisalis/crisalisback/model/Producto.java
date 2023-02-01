package com.crisalis.crisalisback.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.crisalis.crisalisback.dto.ProductoDTO;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("producto")
public class Producto extends ProductoBase{
    private int stock;
    public Producto(String tipo, String nombre, String descripcion, BigDecimal precioBase, int stock) {
        super(tipo, nombre, descripcion, precioBase);
        this.stock = stock;
    }

    public Producto(String nombre, String descripcion, BigDecimal precioBase, int stock) {
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
                .tipo(this.getTipo())
                .build();
    }


}
