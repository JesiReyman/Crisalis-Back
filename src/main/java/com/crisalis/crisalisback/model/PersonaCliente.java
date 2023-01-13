package com.crisalis.crisalisback.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("personaFisica")
public class PersonaCliente extends Cliente{
    @NotNull
    private String nombre;

    @NotNull
    private String apellido;

    @NotNull
    @Column(unique = true)
    private int dni;

    @JsonManagedReference
    @OneToOne(mappedBy="personaCliente", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private EmpresaCliente empresa;

    @Builder
    public PersonaCliente(String nombre, String apellido, int dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    public void setEmpresaCliente(EmpresaCliente empresaCliente){
        this.empresa = empresaCliente;
        this.empresa.setPersonaCliente(this);
    }

    public void eliminarEmpresaCliente(){
        this.empresa = null;
    }


}
