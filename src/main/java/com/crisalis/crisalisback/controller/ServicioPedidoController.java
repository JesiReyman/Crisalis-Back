package com.crisalis.crisalisback.controller;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.crisalis.crisalisback.model.ServicioPedido;
import com.crisalis.crisalisback.service.ServicioPedidoService;

import java.util.List;

@RestController
@RequestMapping("/servicioPedido")
public class ServicioPedidoController {
    
    private ServicioPedidoService servicioPedidoService;

    public ServicioPedidoController(ServicioPedidoService servicioPedidoService) {
        this.servicioPedidoService = servicioPedidoService;
    }

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

    @GetMapping("{idPedido}/lista")
    public ResponseEntity<List<ItemPedidoDto>> servicioPedidosPorIdPedido(@PathVariable("idPedido") long idPedido){
        List<ItemPedidoDto> lista = servicioPedidoService.serviciosDePedido(idPedido);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
