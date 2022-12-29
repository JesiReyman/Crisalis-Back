
package com.crisalis.crisalisback.security.Dto;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NuevoUsuario {
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();
}
