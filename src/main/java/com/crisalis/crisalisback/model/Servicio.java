package com.crisalis.crisalisback.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("servicio")
public class Servicio extends Producto{
    private double precioSoporte;

    
}
