package com.crisalis.crisalisback.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.crisalis.crisalisback.dto.ProductoDTO;
import com.crisalis.crisalisback.dto.ServicioDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("servicio")
public class Servicio extends ProductoBase{
    private double precioSoporte;

    /*@Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "servicio")
    private List<ServicioPedido> listaServiciosPedidos = new ArrayList<ServicioPedido>();*/

    public Servicio(String tipo, String nombre, String descripcion, double precioBase, double precioSoporte) {
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
                .build();
    }
}
