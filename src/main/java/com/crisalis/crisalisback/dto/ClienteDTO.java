package com.crisalis.crisalisback.dto;

import com.crisalis.crisalisback.model.EmpresaCliente;
import com.crisalis.crisalisback.model.PersonaCliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private String nombre;
    private long dniOCuit;

    public ClienteDTO(PersonaCliente personaCliente){
        this.nombre = personaCliente.getNombre();
        this.dniOCuit = personaCliente.getDniOCuit();
    }

    public ClienteDTO(EmpresaCliente empresaCliente){
        this.nombre = empresaCliente.getRazonSocial();
        this.dniOCuit = empresaCliente.getDniOCuit();
    }
}
