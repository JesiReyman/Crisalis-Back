package com.crisalis.crisalisback.controller;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.model.ItemPedido;
import com.crisalis.crisalisback.service.ItemPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("itemsPedidos")
public class ItemPedidoController {
    private ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<ItemPedido>> listaItemsPedidos(){
        List<ItemPedido> listaItems = itemPedidoService.listaItemsPedidos();
        return new ResponseEntity<>(listaItems, HttpStatus.OK);
    }

    @GetMapping("lista/{tipo}")
    public List<ItemPedido> listarPorTipo(@PathVariable("tipo") String tipo){
        List<ItemPedido> listaItems = itemPedidoService.buscarPorTipo(tipo);
        return listaItems;
    }

    @PostMapping("estimarItem")
    public ResponseEntity<ItemPedidoDto> estimarItemPedido(@RequestBody ItemPedidoDto itemPedidoDto){
        ItemPedidoDto itemEstimado = itemPedidoService.calcularItem(itemPedidoDto);
        return new ResponseEntity<>(itemEstimado, HttpStatus.OK);
    }

    @GetMapping("{idPedido}/lista")
    public ResponseEntity<List<ItemPedidoDto>> itemsPedidos(@PathVariable("idPedido") long idPedido){
        List<ItemPedidoDto> lista = itemPedidoService.obtenerItemsDePedido(idPedido);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
