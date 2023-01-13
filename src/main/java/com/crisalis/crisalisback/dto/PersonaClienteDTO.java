package com.crisalis.crisalisback.dto;

import com.crisalis.crisalisback.model.Cliente;
import com.crisalis.crisalisback.model.PersonaCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PersonaClienteDTO {
    private String nombre;
    private String apellido;
    private int dni;

    public PersonaClienteDTO(PersonaCliente personaCliente){
        this.nombre = personaCliente.getNombre();
        this.apellido = personaCliente.getApellido();
        this.dni = personaCliente.getDni();
    }

    public static PersonaCliente dtoAPersonaCliente(PersonaClienteDTO personaClienteDTO){
        return PersonaCliente.builder()
                .nombre(personaClienteDTO.nombre)
                .apellido(personaClienteDTO.apellido)
                .dni(personaClienteDTO.dni)
                .build();
    }
}
