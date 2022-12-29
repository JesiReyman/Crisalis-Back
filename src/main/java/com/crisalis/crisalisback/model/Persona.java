package com.crisalis.crisalisback.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.crisalis.crisalisback.security.entity.UsuarioLogin;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@MappedSuperclass
@Getter @Setter
/*@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo", 
  discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("persona")
@Entity*/
public abstract class Persona {

    @NotNull
    private String nombre;
    @NotNull
    private String apellido;


    /*@Getter (value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<Pedido> listaDeProductos = new ArrayList<Pedido>();*/

    public Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
 
}
