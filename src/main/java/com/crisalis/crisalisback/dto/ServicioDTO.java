package com.crisalis.crisalisback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ServicioDTO {
    private String nombre;
    private String descripcion;
    private double precioBase;
    private double precioSoporte;
}
