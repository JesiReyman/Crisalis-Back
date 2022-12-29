package com.crisalis.crisalisback.controller;

import com.crisalis.crisalisback.model.Cliente;
import com.crisalis.crisalisback.model.EmpresaCliente;
import com.crisalis.crisalisback.service.EmpresaClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("empresaCliente")
public class EmpresaClienteController {
    private EmpresaClienteService empresaClienteService;

    public EmpresaClienteController(EmpresaClienteService empresaClienteService) {
        this.empresaClienteService = empresaClienteService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{idCliente}/nueva")
    public ResponseEntity<EmpresaCliente> agregarEmpresaCliente(@PathVariable("idCliente") Long idCliente, @RequestBody EmpresaCliente empresaCliente) {
        EmpresaCliente nuevaEmpresaCliente = empresaClienteService.nuevaEmpresaCliente(idCliente, empresaCliente);
        return new ResponseEntity<>(nuevaEmpresaCliente, HttpStatus.OK);
    }

    @GetMapping("{idEmpresa}")
    public ResponseEntity<EmpresaCliente> traerEmpresaCliente(@PathVariable("idEmpresa") Long idEmpresa){
        EmpresaCliente empresa = empresaClienteService.trearEmpresaCliente(idEmpresa);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @PutMapping("{idEmpresa}/{idCliente}")
    public ResponseEntity<EmpresaCliente> editarClienteAsociado(@PathVariable("idEmpresa") Long idEmpresa, @PathVariable("idCliente") Long idCliente){
        EmpresaCliente empresa = empresaClienteService.editarClienteAsociado(idEmpresa, idCliente);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @PutMapping("editar/{idEmpresa}")
    public ResponseEntity<EmpresaCliente> editarEmpresa(@PathVariable("idEmpresa") Long idEmpresa, @RequestBody EmpresaCliente empresa){
        EmpresaCliente empresaEditada = empresaClienteService.editarEmpresa(idEmpresa, empresa);
        return new ResponseEntity<>(empresaEditada, HttpStatus.OK);
    }
}
