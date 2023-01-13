package com.crisalis.crisalisback.dto;

import com.crisalis.crisalisback.model.EmpresaCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class EmpresaClienteDTO {
    private String razonSocial;
    private LocalDate fechaInicio;
    private long cuit;

    public EmpresaClienteDTO(EmpresaCliente empresaCliente) {
        this.razonSocial = empresaCliente.getRazonSocial();
        this.fechaInicio = empresaCliente.getFechaInicio();
        this.cuit = empresaCliente.getCuit();

    }

    public static EmpresaCliente dtoAEmpresaCliente(EmpresaClienteDTO empresaClienteDTO){
        return EmpresaCliente.builder()
                .razonSocial(empresaClienteDTO.razonSocial)
                .fechaInicio(empresaClienteDTO.fechaInicio)
                .cuit(empresaClienteDTO.cuit)
                .build();
    }
}
