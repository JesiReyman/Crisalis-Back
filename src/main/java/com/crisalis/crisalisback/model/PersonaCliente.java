package com.crisalis.crisalisback.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("persona")
public class PersonaCliente extends Cliente{
    private String nombre;
    private String apellido;

    @JsonManagedReference
    @OneToOne(mappedBy="personaCliente", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private EmpresaCliente empresa;


    @Builder
    public PersonaCliente(String tipo, long dniOCuit, String nombre, String apellido) {
        super(tipo, dniOCuit);
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public void setEmpresaCliente(EmpresaCliente empresaCliente){
        this.empresa = empresaCliente;
        this.empresa.setPersonaCliente(this);
    }

    public void eliminarEmpresaCliente(){
        this.empresa = null;
    }


}
