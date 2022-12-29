package com.crisalis.crisalisback.controller;

import java.util.List;

import com.crisalis.crisalisback.repository.IPedidoRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crisalis.crisalisback.model.Pedido;
import com.crisalis.crisalisback.service.PedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    private PedidoService pedidoService;
    private final IPedidoRepositorio iPedidoRepositorio;

    public PedidoController(PedidoService pedidoService,
                            IPedidoRepositorio iPedidoRepositorio) {
        this.pedidoService = pedidoService;
        this.iPedidoRepositorio = iPedidoRepositorio;
    }

    /*@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{idPersona}/nuevo")
    public ResponseEntity<Pedido> agregarPedido(@PathVariable("idPersona") Long idPersona) {
        Pedido nuevoPedido = pedidoService.agregarPedidoAPersona(idPersona);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.OK);
    }*/

    @GetMapping("/{idPersona}/lista")
    public ResponseEntity<List<Pedido>> listarPedidosDePersona(@PathVariable("idPersona") Long idPersona) {
        List<Pedido> listaPedido = pedidoService.listarPedidosPorClienteFechaAsc(idPersona);
        return new ResponseEntity<>(listaPedido, HttpStatus.OK);
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<Pedido> detallePedido(@PathVariable("idPedido") Long idPedido){
        Pedido pedido = pedidoService.detallePedido(idPedido);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @DeleteMapping("borrar/{idPedido}")
    public void eliminarPedido(@PathVariable("idPedido") Long idPedido){
        pedidoService.eliminarPedido(idPedido);
    }
}
