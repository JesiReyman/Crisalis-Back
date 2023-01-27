package com.crisalis.crisalisback.model;


import java.math.BigDecimal;

public class Adicional {

    private static BigDecimal incrementoGarantia = new BigDecimal("0.02");
    private static BigDecimal descuentoProducto = new BigDecimal("0.1");

    public static BigDecimal cargoGarantia(BigDecimal precio, int aniosGarantia){
        return precio.multiply(new BigDecimal(aniosGarantia)).multiply(incrementoGarantia);
    }

    public static BigDecimal descuentoProducto(BigDecimal precioProducto){

        return precioProducto.multiply(descuentoProducto);
    }
}
