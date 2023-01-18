package com.crisalis.crisalisback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDto {
    private int cantidad;
    private int aniosDeGarantia;
    private String nombre;

    private double precioBase;

    private double precioFinal;

}
