package com.crisalis.crisalisback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private String nombre;
    private String descripcion;
    private int stock;
    private BigDecimal precioBase;
    private String tipo;
}
