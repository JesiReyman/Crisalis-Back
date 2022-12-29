
package com.crisalis.crisalisback.security.entity;

import com.crisalis.crisalisback.model.Persona;

import com.sun.istack.NotNull;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name="USUARIO")
public class UsuarioLogin extends Persona{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public UsuarioLogin(String nombre, String apellido, String nombreUsuario, String email, String password) {
        super(nombre, apellido);
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    /*public void addPersona(Persona persona) {
        // perfil.add(perfil);
        persona.setUsuario(this);
    }*/

}
