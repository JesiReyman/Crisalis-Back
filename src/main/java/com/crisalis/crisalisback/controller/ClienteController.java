package com.crisalis.crisalisback.controller;

import com.crisalis.crisalisback.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.crisalis.crisalisback.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevo")
    public ResponseEntity<Cliente> agregarCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.agregarCliente(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.OK);
    }

    @GetMapping("{idCliente}")
    public ResponseEntity<Cliente> traerCliente(@PathVariable("idCliente") Long idCliente){
        Cliente cliente = clienteService.traerCliente(idCliente);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
}
