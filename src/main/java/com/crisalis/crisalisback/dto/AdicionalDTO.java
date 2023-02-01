package com.crisalis.crisalisback.dto;

import com.crisalis.crisalisback.enums.AplicaAdicional;
import com.crisalis.crisalisback.enums.TipoAdicional;
import com.crisalis.crisalisback.model.Adicional;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@AllArgsConstructor

public class AdicionalDTO {
    @NotEmpty
    private String nombre;
    private BigDecimal porcentaje;
    private AplicaAdicional aplica;

    private TipoAdicional tipo;

    public AdicionalDTO(Adicional adicional){
        this.nombre = adicional.getNombre();
        this.porcentaje = adicional.getPorcentaje();
        //this.aplica = impuesto.getAplica().toString() ;
        this.aplica = adicional.getAplica();
        this.tipo = adicional.getTipo();
    }

    public static Adicional dtoAImpuesto(AdicionalDTO adicionalDTO){
        return Adicional.builder()
                .nombre(adicionalDTO.nombre)
                .porcentaje(adicionalDTO.porcentaje)
                .aplica(adicionalDTO.aplica)
                .tipo(adicionalDTO.tipo)
                .build();
    }
}
