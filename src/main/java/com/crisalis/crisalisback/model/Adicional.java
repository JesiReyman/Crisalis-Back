package com.crisalis.crisalisback.model;


public class Adicional {
    private static double IVA = 0.21;
    private static double IIBB = 0.035;
    private static double incrementoGarantia = 0.02;
    private static double descuentoProducto = 0.1;

    
    public static double impuestoIVA(double precioBase){
        
        return IVA * precioBase;
    }

    public static double impuestoIIBB(Servicio servicio){
        
        return servicio.getPrecioBase() * IIBB;
    }

    public double cargoGarantia(ProductoPedido productoPedido){
        Producto producto = productoPedido.getProducto();
        double precioBase = producto.getPrecioBase();
        double precioTotalGarantia = incrementoGarantia * precioBase * productoPedido.getAniosDeGarantia();
        return precioTotalGarantia;
    }

    public static double descuentoProducto(ProductoPedido producto){
        double descuento = producto.getPrecioFinalUnitario() * descuentoProducto;
        return descuento;
    }
}
