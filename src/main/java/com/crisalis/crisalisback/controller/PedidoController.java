package com.crisalis.crisalisback.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.crisalisback.model.Pedido;
import com.crisalis.crisalisback.service.PedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{idPersona}/nuevo")
    public ResponseEntity<Pedido> agregarPedido(@PathVariable("idPersona") Long idPersona) {
        Pedido nuevoPedido = pedidoService.agregarPedidoAPersona(idPersona);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.OK);
    }

    @GetMapping("/{idPersona}/lista")
    public ResponseEntity<List<Pedido>> listarPedidosDePersona(@PathVariable("idPersona") Long idPersona) {
        List<Pedido> listaPedido = pedidoService.listarPedidosPorPersonaFechaAsc(idPersona);
        return new ResponseEntity<>(listaPedido, HttpStatus.OK);
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<Pedido> detallePedido(@PathVariable("idPedido") Long idPedido){
        Pedido pedido = pedidoService.detallePedido(idPedido);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }
}
