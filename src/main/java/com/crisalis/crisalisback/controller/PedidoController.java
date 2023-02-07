package com.crisalis.crisalisback.controller;

import java.util.List;

import com.crisalis.crisalisback.dto.EstadoDTO;
import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.dto.PedidoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crisalis.crisalisback.model.Pedido;
import com.crisalis.crisalisback.service.PedidoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    private PedidoService pedidoService;


    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("lista")
    public ResponseEntity<Page<PedidoDTO>> listarPedidos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCreacion").descending());
        Page<PedidoDTO> listaPedidos = pedidoService.listarPedidos(pageable);
        return new ResponseEntity<>(listaPedidos, HttpStatus.OK);
    }

    @PostMapping("/{dniOCuitCliente}/nuevo")
    public ResponseEntity<Pedido> crearPedido(@PathVariable("dniOCuitCliente") long dniOCuitCliente, @Valid @RequestBody List<ItemPedidoDto> listaItems){
        Pedido nuevoPedido = pedidoService.crearPedido(dniOCuitCliente, listaItems);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.OK);
    }

    @GetMapping("/{idPersona}/lista")
    public ResponseEntity<Page<PedidoDTO>> listarPedidosDePersona(@PathVariable("idPersona") Long idPersona,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PedidoDTO> listaPedido = pedidoService.pedidosDeCliente(idPersona, pageable);
        return new ResponseEntity<>(listaPedido, HttpStatus.OK);
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<Pedido> detallePedido(@PathVariable("idPedido") Long idPedido){
        Pedido pedido = pedidoService.encontrarPedido(idPedido);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @DeleteMapping("borrar/{idPedido}")
    public void eliminarPedido(@PathVariable("idPedido") Long idPedido){
        pedidoService.eliminarPedido(idPedido);
    }

    @PutMapping("{idPedido}/cambiarEstado")
    public void cambiarEstado(@PathVariable("idPedido") Long idPedido, @RequestBody EstadoDTO estadoDTO){
        pedidoService.cambiarEstado(idPedido, estadoDTO);
    }

    @PutMapping("editar/{idPedido}")
    public ResponseEntity<Pedido> editarPedido(@PathVariable("idPedido") Long idPedido, @RequestBody List<ItemPedidoDto> listaItems){
        Pedido pedido = pedidoService.editarPedido(idPedido, listaItems);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }
}
