package com.crisalis.crisalisback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class ServicioDTO {
    private String nombre;
    private String descripcion;
    private BigDecimal precioBase;
    private BigDecimal precioSoporte;
}
