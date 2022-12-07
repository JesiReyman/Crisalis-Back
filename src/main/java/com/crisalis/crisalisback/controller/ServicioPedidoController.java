package com.crisalis.crisalisback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.crisalisback.model.ServicioPedido;
import com.crisalis.crisalisback.service.ServicioPedidoService;

@RestController
@RequestMapping("/pedirServicio")
public class ServicioPedidoController {
    
    private ServicioPedidoService servicioPedidoService;

    public ServicioPedidoController(ServicioPedidoService servicioPedidoService) {
        this.servicioPedidoService = servicioPedidoService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{idPedido}/{idServicio}")
    public ResponseEntity<ServicioPedido> pedirServicio(@RequestBody int cantidad, @PathVariable("idPedido") Long idPedido,
                                                        @PathVariable("idServicio") Long idServicio) {
        ServicioPedido nuevoServicioPedido = servicioPedidoService.agregarServicioPedido(cantidad, idPedido, idServicio);
        return new ResponseEntity<>(nuevoServicioPedido, HttpStatus.OK);
    }
}
