package com.crisalis.crisalisback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/{idPedido}/nuevo/{idServicio}")
    public ResponseEntity<ServicioPedido> pedirServicio(@RequestBody ServicioPedido servicioPedido, @PathVariable("idPedido") Long idPedido, @PathVariable("idServicio") Long idServicio) {
        ServicioPedido nuevoServicioPedido = servicioPedidoService.agregarServicioPedido(servicioPedido, idPedido, idServicio);
        return new ResponseEntity<>(nuevoServicioPedido, HttpStatus.OK);
    }
}
