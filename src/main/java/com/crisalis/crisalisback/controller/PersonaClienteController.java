package com.crisalis.crisalisback.controller;

import com.crisalis.crisalisback.dto.PersonaClienteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.crisalis.crisalisback.service.PersonaClienteService;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class PersonaClienteController {
    private PersonaClienteService personaClienteService;

    public PersonaClienteController(PersonaClienteService personaClienteService) {
        this.personaClienteService = personaClienteService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevo")
    public ResponseEntity<PersonaClienteDTO> agregarCliente(@RequestBody PersonaClienteDTO cliente) {
        PersonaClienteDTO nuevoCliente = personaClienteService.agregarCliente(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{dniCliente}")
    public ResponseEntity<PersonaClienteDTO> buscarClientePorDni(@PathVariable("dniCliente") Long dniCliente){
        PersonaClienteDTO cliente = personaClienteService.buscarClientePorDni(dniCliente);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("lista")
    public ResponseEntity<List<PersonaClienteDTO>> listaClientes(){
        List<PersonaClienteDTO> listaClientes = personaClienteService.listaClientes();
        return new ResponseEntity<>(listaClientes, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("editar/{dniCliente}")
    public ResponseEntity<PersonaClienteDTO> editarCliente(@PathVariable("dniCliente") long dniCliente, @RequestBody PersonaClienteDTO personaClienteDTO){
        PersonaClienteDTO cliente = personaClienteService.editarCliente(dniCliente, personaClienteDTO);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("eliminar/{dniCliente}")
    public void eliminarCliente(@PathVariable("dniCliente") Long dniCliente){
        personaClienteService.eliminarCliente(dniCliente);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{cuitEmpresa}/asociar/{dniCliente}")
    public void asociarAEmpresa(@PathVariable("cuitEmpresa") Long cuitEmpresa, @PathVariable("dniCliente") long dniCliente){
        personaClienteService.asociarAEmpresa(cuitEmpresa, dniCliente);
    }
}
