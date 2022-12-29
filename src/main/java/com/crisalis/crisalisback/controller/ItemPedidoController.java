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

    /*@PostMapping("{idPersona}/{idEmpresa}/guardarLista")
    public void guardarListaItems(@PathVariable("idPersona") Long idPersona, @RequestBody List<ItemPedidoDto> listaItems){
        this.itemPedidoService.guardarItemsPedidos(listaItems, idPersona);
    }*/

    @GetMapping("lista/{tipo}")
    public List<ItemPedido> listarPorTipo(@PathVariable("tipo") String tipo){
        List<ItemPedido> listaItems = itemPedidoService.buscarPorTipo(tipo);
        return listaItems;
    }
}
