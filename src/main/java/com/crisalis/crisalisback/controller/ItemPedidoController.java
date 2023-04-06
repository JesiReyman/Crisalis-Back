package com.crisalis.crisalisback.controller;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.model.ItemPedido;
import com.crisalis.crisalisback.model.ServicioPedido;
import com.crisalis.crisalisback.service.ItemPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("itemsPedidos")
public class ItemPedidoController {
    private ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lista")
    public ResponseEntity<List<ItemPedido>> listaItemsPedidos(){
        List<ItemPedido> listaItems = itemPedidoService.listaItemsPedidos();
        return new ResponseEntity<>(listaItems, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("lista/{tipo}")
    public List<ItemPedido> listarPorTipo(@PathVariable("tipo") String tipo){
        List<ItemPedido> listaItems = itemPedidoService.buscarPorTipo(tipo);
        return listaItems;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("estimarItem/{dniOCuitCliente}")
    public ResponseEntity<ItemPedidoDto> estimarItemPedido(@PathVariable("dniOCuitCliente") long dniOCuitCLiente , @RequestBody ItemPedidoDto itemPedidoDto){
        ItemPedidoDto itemEstimado = itemPedidoService.calcularItem(itemPedidoDto, dniOCuitCLiente);
        return new ResponseEntity<>(itemEstimado, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{idPedido}/lista")
    public ResponseEntity<List<ItemPedidoDto>> itemsPedidos(@PathVariable("idPedido") long idPedido){
        List<ItemPedidoDto> lista = itemPedidoService.obtenerItemsDePedido(idPedido);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    /*@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("servicioActivo/{idCliente}")
    public ResponseEntity<ItemPedido> servicioActivo(@PathVariable("idCliente") long idCliente){
        ItemPedido servicioActivo = itemPedidoService.buscarServicioActivo(idCliente);
        return new ResponseEntity<>(servicioActivo, HttpStatus.OK);
    }*/
}
