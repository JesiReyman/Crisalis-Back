package com.crisalis.crisalisback.controller;

import com.crisalis.crisalisback.dto.EmpresaClienteDTO;
import com.crisalis.crisalisback.dto.PersonaClienteDTO;
import com.crisalis.crisalisback.model.EmpresaCliente;
import com.crisalis.crisalisback.service.EmpresaClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("empresa")
public class EmpresaClienteController {
    private EmpresaClienteService empresaClienteService;

    public EmpresaClienteController(EmpresaClienteService empresaClienteService) {
        this.empresaClienteService = empresaClienteService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("nueva")
    public ResponseEntity<EmpresaClienteDTO> agregarEmpresaCliente(@RequestBody EmpresaClienteDTO empresaCliente) {
        EmpresaClienteDTO nuevaEmpresaCliente = empresaClienteService.nuevaEmpresaCliente(empresaCliente);
        return new ResponseEntity<>(nuevaEmpresaCliente, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{cuitEmpresa}")
    public ResponseEntity<EmpresaClienteDTO> traerEmpresaCliente(@PathVariable("cuitEmpresa") Long cuitEmpresa){
        EmpresaClienteDTO empresa = empresaClienteService.traerEmpresaCliente(cuitEmpresa);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("lista")
    public ResponseEntity<List<EmpresaClienteDTO>> listaEmpresas(){
        List<EmpresaClienteDTO> listaEmpresas = empresaClienteService.listaEmpresas();
        return new ResponseEntity<>(listaEmpresas, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{cuitEmpresa}/personaAsociada")
    public ResponseEntity<PersonaClienteDTO> getPersonaAsociada(@PathVariable("cuitEmpresa") Long cuitEmpresa){
        PersonaClienteDTO personaClienteDTO = empresaClienteService.mostrarPersonaAsociada(cuitEmpresa);
        return new ResponseEntity<>(personaClienteDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{idEmpresa}/asociar/{idCliente}")
    public ResponseEntity<EmpresaCliente> editarClienteAsociado(@PathVariable("idEmpresa") Long idEmpresa, @PathVariable("idCliente") Long idCliente){
        EmpresaCliente empresa = empresaClienteService.editarClienteAsociado(idEmpresa, idCliente);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("editar/{cuitEmpresa}")
    public ResponseEntity<EmpresaClienteDTO> editarEmpresa(@PathVariable("cuitEmpresa") Long cuitEmpresa, @RequestBody EmpresaClienteDTO empresa){
        EmpresaClienteDTO empresaEditada = empresaClienteService.editarEmpresa(cuitEmpresa, empresa);
        return new ResponseEntity<>(empresaEditada, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("eliminar/{cuitEmpresa}")
    public void eliminarEmpresaCliente(@PathVariable("cuitEmpresa") Long cuitEmpresa){
        empresaClienteService.eliminarEmpresa(cuitEmpresa);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{cuitEmpresa}/asociar")
    public ResponseEntity<EmpresaCliente> asociarNuevaPersona(@PathVariable("cuitEmpresa") long cuitEmpresa, @RequestBody PersonaClienteDTO persona){
        EmpresaCliente empresa = empresaClienteService.setPersonaAEmpresa(cuitEmpresa, persona);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }
}
