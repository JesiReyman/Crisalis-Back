package com.crisalis.crisalisback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private String nombre;
    private String descripcion;
    private int stock;
    private double precioBase;
}
