
package com.crisalis.crisalisback.security.service;

import com.crisalis.crisalisback.model.Persona;
import com.crisalis.crisalisback.repository.IPersona;
import com.crisalis.crisalisback.security.entity.UsuarioLogin;
import com.crisalis.crisalisback.security.repository.UsuarioRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioLoginService {
    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    IPersona iPersona;
    
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
       Persona nuevaPersona = new Persona();
       nuevaPersona.setNombre(nuevoUsuario.getNombre());
       usuarioRepository.save(nuevoUsuario);
       iPersona.save(nuevaPersona);
    }
    
    public void delete(String nombreUsuario){
        UsuarioLogin usuario = usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        //Long id = usuario.getId();
        usuarioRepository.delete(usuario);
    }
}
