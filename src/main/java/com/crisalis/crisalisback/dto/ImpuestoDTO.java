package com.crisalis.crisalisback.dto;

import com.crisalis.crisalisback.enums.AplicaImpuesto;
import com.crisalis.crisalisback.model.Impuesto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@AllArgsConstructor

public class ImpuestoDTO {
    @NotEmpty
    private String nombre;
    private BigDecimal porcentaje;
    private AplicaImpuesto aplica;

    public ImpuestoDTO(Impuesto impuesto){
        this.nombre = impuesto.getNombre();
        this.porcentaje = impuesto.getPorcentaje();
        //this.aplica = impuesto.getAplica().toString() ;
        this.aplica = impuesto.getAplica();
    }

    public static Impuesto dtoAImpuesto(ImpuestoDTO impuestoDTO){
        //AplicaImpuesto aplica = AplicaImpuesto.forValue(impuestoDTO.aplica);
        return Impuesto.builder()
                .nombre(impuestoDTO.nombre)
                .porcentaje(impuestoDTO.porcentaje)
                .aplica(impuestoDTO.aplica)
                .build();
    }
}
