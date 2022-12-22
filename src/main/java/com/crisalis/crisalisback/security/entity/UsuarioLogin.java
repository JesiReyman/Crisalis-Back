
package com.crisalis.crisalisback.security.entity;

import com.crisalis.crisalisback.model.Persona;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.sun.istack.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name="USUARIO")
public class UsuarioLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    @Column(unique = true)
    private String nombreUsuario;
    @NotNull
    private String email;
    @NotNull
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", 
                joinColumns = @JoinColumn(name = "usuario_id"),
                inverseJoinColumns = @JoinColumn(name = "rol_id") )
    
    private Set<Rol> roles = new HashSet<>();

    /*@JsonManagedReference
    @OneToOne(mappedBy="usuario", cascade = {CascadeType.MERGE}, orphanRemoval = true)
    private Persona persona;*/
    
    public UsuarioLogin(String nombre, String nombreUsuario, String email, String password) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
    }

    /*public void addPersona(Persona persona) {
        // perfil.add(perfil);
        persona.setUsuario(this);
    }*/

}
