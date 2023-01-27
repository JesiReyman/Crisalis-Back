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
    private long dniOCuit;

    public PersonaClienteDTO(PersonaCliente personaCliente){
        this.nombre = personaCliente.getNombre();
        this.apellido = personaCliente.getApellido();
        this.dniOCuit = personaCliente.getDniOCuit();
    }

    public static PersonaCliente dtoAPersonaCliente(PersonaClienteDTO personaClienteDTO){
        return PersonaCliente.builder()
                .nombre(personaClienteDTO.nombre)
                .apellido(personaClienteDTO.apellido)
                .dniOCuit(personaClienteDTO.dniOCuit)
                .build();
    }
}
