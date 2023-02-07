package com.crisalis.crisalisback.Exception;

public class ExcepcionElementoVacio extends RuntimeException{
    private static final String DESCRIPCION = "Elemento vacío (400)";

    public ExcepcionElementoVacio(String detalle){
        super(DESCRIPCION + ". " + detalle);
    }
}
