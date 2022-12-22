package com.crisalis.crisalisback.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.crisalis.crisalisback.security.entity.UsuarioLogin;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo", 
  discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("persona")
@Entity
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nombre;
    private String apellido;
    private long dni;

    /*@JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private UsuarioLogin usuario;*/

    /*@Getter (value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Empresa empresa;*/

    @Getter (value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<Pedido> listaDeProductos = new ArrayList<Pedido>();

    public Persona(long id, String nombre, String apellido, long dni) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    

    
 
}
