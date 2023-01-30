package com.crisalis.crisalisback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.crisalis.crisalisback.model.ServicioPedido;
import com.crisalis.crisalisback.service.ServicioPedidoService;

@RestController
@RequestMapping("/servicioPedido")
public class ServicioPedidoController {
    
    private ServicioPedidoService servicioPedidoService;

    public ServicioPedidoController(ServicioPedidoService servicioPedidoService) {
        this.servicioPedidoService = servicioPedidoService;
    }

    /*@PreAuthorize("hasRole('USER')")
    @PostMapping("/{idPedido}/{idServicio}")
    public ResponseEntity<ServicioPedido> pedirServicio(@RequestBody int cantidad, @PathVariable("idPedido") Long idPedido,
                                                        @PathVariable("idServicio") Long idServicio) {
        ServicioPedido nuevoServicioPedido = servicioPedidoService.agregarServicioPedido(cantidad, idPedido, idServicio);
        return new ResponseEntity<>(nuevoServicioPedido, HttpStatus.OK);
    }*/

    @DeleteMapping("/borrar/{idServicioPedido}")
    public void borrarServicioPedido(@PathVariable("idServicioPedido") Long idServicioPedido){
        servicioPedidoService.borrarServicioPedido(idServicioPedido);
    }

    @PutMapping("cambiarEstado/{nombreServicio}/{idPedido}")
    public ResponseEntity<ServicioPedido>  cambiarEstadoServicio(@PathVariable("nombreServicio") String nombreServicio,
                                                @PathVariable("idPedido") Long idPedido, @RequestBody boolean activo){
        ServicioPedido servicioPedido = servicioPedidoService.cambiarEstadoServicio(nombreServicio, idPedido, activo);
        return new ResponseEntity<>(servicioPedido, HttpStatus.OK);
    }
}
