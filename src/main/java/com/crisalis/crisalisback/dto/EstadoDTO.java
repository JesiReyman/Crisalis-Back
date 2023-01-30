package com.crisalis.crisalisback.dto;

import com.crisalis.crisalisback.enums.EstadoDePedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class EstadoDTO {
    private EstadoDePedido estado;

    public EstadoDTO(EstadoDePedido estado) {
        this.estado = estado;
    }
}
