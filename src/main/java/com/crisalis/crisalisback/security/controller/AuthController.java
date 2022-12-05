
package com.crisalis.crisalisback.security.controller;

import com.crisalis.crisalisback.security.Dto.JwtDto;
import com.crisalis.crisalisback.security.Dto.LoginUsuario;
import com.crisalis.crisalisback.security.Dto.NuevoUsuario;
import com.crisalis.crisalisback.security.entity.Rol;
import com.crisalis.crisalisback.security.entity.UsuarioLogin;
import com.crisalis.crisalisback.security.enums.RolNombre;
import com.crisalis.crisalisback.security.jwt.JwtProvider;
import com.crisalis.crisalisback.security.service.RolService;
import com.crisalis.crisalisback.security.service.UsuarioLoginService;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    AuthenticationManager authManager;
    
    @Autowired
    UsuarioLoginService usuarioLoginService;
    
    @Autowired
    RolService rolService;
    
    @Autowired
    JwtProvider jwtProvider;
    
    
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario,
                BindingResult bindingResult
            ){
        if (bindingResult.hasErrors())
            return new ResponseEntity<Mensaje>(new Mensaje("Campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        
        if (usuarioLoginService.existByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity<Mensaje>(new Mensaje("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        
        if (usuarioLoginService.existByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity<Mensaje>(new Mensaje("Ese email ya está registrado"), HttpStatus.BAD_REQUEST);
        
        UsuarioLogin usuario = new UsuarioLogin(nuevoUsuario.getNombre(), 
                nuevoUsuario.getNombreUsuario(), 
                nuevoUsuario.getEmail(),
                passwordEncoder.encode(nuevoUsuario.getPassword()));
        
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioLoginService.save(usuario);
        
        return new ResponseEntity<Mensaje>(new Mensaje("Usuario guardado"), HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity<Mensaje>(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
     
        JwtDto jwtDto = new JwtDto(jwt);
        
        return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
    }
    
    //@PreAuthorize("hasRole('ADMIN') || #nombreUsuario == authentication.principal.username")
    @DeleteMapping("/delete/{nombreUsuario}")
    public void delete(@PathVariable(value = "nombreUsuario") String nombreUsuario){
        usuarioLoginService.delete(nombreUsuario);
    }
}
