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
    private long dniOCuit;
    private long dniPersona;

    public EmpresaClienteDTO(EmpresaCliente empresaCliente) {
        this.razonSocial = empresaCliente.getRazonSocial();
        this.fechaInicio = empresaCliente.getFechaInicio();
        this.dniOCuit = empresaCliente.getDniOCuit();
        if (empresaCliente.getPersonaCliente() != null){
            this.dniPersona = empresaCliente.getPersonaCliente().getDniOCuit();
        }
    }

    public static EmpresaCliente dtoAEmpresaCliente(EmpresaClienteDTO empresaClienteDTO){
        return EmpresaCliente.builder()
                .razonSocial(empresaClienteDTO.razonSocial)
                .fechaInicio(empresaClienteDTO.fechaInicio)
                .dniOCuit(empresaClienteDTO.dniOCuit)
                .build();
    }
}
