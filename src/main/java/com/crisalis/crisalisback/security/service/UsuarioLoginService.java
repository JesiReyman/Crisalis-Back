
package com.crisalis.crisalisback.security.service;

import com.crisalis.crisalisback.Exception.ExcepcionElementoVacio;
import com.crisalis.crisalisback.repository.IPersonaClienteRepository;
import com.crisalis.crisalisback.security.Dto.LoginUsuario;
import com.crisalis.crisalisback.security.Dto.NuevoUsuario;
import com.crisalis.crisalisback.security.controller.Mensaje;
import com.crisalis.crisalisback.security.entity.Rol;
import com.crisalis.crisalisback.security.entity.UsuarioLogin;
import com.crisalis.crisalisback.security.enums.RolNombre;
import com.crisalis.crisalisback.security.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioLoginService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RolService rolService;
    
    public Optional<UsuarioLogin> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
    
    public boolean existByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
    
    public boolean existByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }
    
    public void save(UsuarioLogin usuario){
       UsuarioLogin nuevoUsuario = usuario;
       usuarioRepository.save(nuevoUsuario);
    }
    
    public void delete(String nombreUsuario){
        UsuarioLogin usuario = usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        //Long id = usuario.getId();
        usuarioRepository.delete(usuario);
    }

    public Boolean checkUsuarioLogin(NuevoUsuario nuevoUsuario){
        if(StringUtils.isEmpty(nuevoUsuario.getNombre())){
            throw new ExcepcionElementoVacio("El nombre es vacio");
        }
        if(StringUtils.isEmpty(nuevoUsuario.getApellido())){
            throw new ExcepcionElementoVacio("El apellido es vacio");
        }
        if(StringUtils.isEmpty(nuevoUsuario.getEmail())){
            throw new ExcepcionElementoVacio("El email es vacio");
        }
        if(StringUtils.isEmpty(nuevoUsuario.getNombreUsuario())){
            throw new ExcepcionElementoVacio("El nombre de usuario es vacio");
        }
        if(StringUtils.isEmpty(nuevoUsuario.getPassword())){
            throw new ExcepcionElementoVacio("La contraseña es vacia");
        }
        return Boolean.TRUE;
    }

    public ResponseEntity<?> nuevoUsuario(NuevoUsuario nuevoUsuario){
        if(checkUsuarioLogin(nuevoUsuario)){
            if (existByNombreUsuario(nuevoUsuario.getNombreUsuario()))
                return new ResponseEntity<Mensaje>(new Mensaje("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);

            if (existByEmail(nuevoUsuario.getEmail()))
                return new ResponseEntity<Mensaje>(new Mensaje("Ese email ya está registrado"), HttpStatus.BAD_REQUEST);

            UsuarioLogin usuario = new UsuarioLogin(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(),
                    nuevoUsuario.getNombreUsuario(),
                    nuevoUsuario.getEmail(),
                    passwordEncoder.encode(nuevoUsuario.getPassword()));

            Set<Rol> roles = new HashSet<>();
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());

        /*if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());*/
            usuario.setRoles(roles);
            save(usuario);
        }
        return new ResponseEntity<Mensaje>(new Mensaje("Usuario guardado"), HttpStatus.CREATED);
    }
}
