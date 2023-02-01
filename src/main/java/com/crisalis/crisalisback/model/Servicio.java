package com.crisalis.crisalisback.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.crisalis.crisalisback.dto.ProductoDTO;
import com.crisalis.crisalisback.dto.ServicioDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("servicio")
public class Servicio extends ProductoBase{
    private BigDecimal precioSoporte;

    public Servicio(String tipo, String nombre, String descripcion, BigDecimal precioBase, BigDecimal precioSoporte) {
        super(tipo, nombre, descripcion, precioBase);
        this.precioSoporte = precioSoporte;
    }

    public Servicio(ServicioDTO servicioDTO){
        super(servicioDTO.getNombre(), servicioDTO.getDescripcion(), servicioDTO.getPrecioBase());
        this.precioSoporte = servicioDTO.getPrecioSoporte();
    }

    public ServicioDTO servicioAservicioDTO(){
        return ServicioDTO.builder()
                .nombre(this.getNombre())
                .descripcion(this.getDescripcion())
                .precioBase(this.getPrecioBase())
                .precioSoporte(this.precioSoporte)
                .tipo(this.getTipo())
                .build();
    }
}
