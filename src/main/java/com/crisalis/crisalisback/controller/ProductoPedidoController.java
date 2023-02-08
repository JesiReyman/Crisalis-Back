package com.crisalis.crisalisback.controller;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.model.ItemPedido;
import com.crisalis.crisalisback.model.ProductoPedido;
import com.crisalis.crisalisback.service.ProductoPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productoPedido")
public class ProductoPedidoController {
    private ProductoPedidoService productoPedidoService;

    public ProductoPedidoController(ProductoPedidoService productoPedidoService) {
        this.productoPedidoService = productoPedidoService;
    }

    /*@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{idPedido}/{idProducto}")
    public ResponseEntity<ProductoPedido> pedirProducto(@RequestBody ProductoPedido productoPedido, @PathVariable("idPedido") Long idPedido,
                                                        @PathVariable("idProducto") Long idProducto) {
        ProductoPedido nuevoProductoPedido = productoPedidoService.agregarProductoPedido(productoPedido, idPedido, idProducto);
        return new ResponseEntity<>(nuevoProductoPedido, HttpStatus.OK);
    }*/

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("borrar/{idProductoPedido}")
    public void eliminarProductoPedido(@PathVariable("idProductoPedido") Long idProductoPedido){
        productoPedidoService.borrarProductoPedido(idProductoPedido);
    }

    /*@GetMapping("{idCliente}/{idProducto}")
    public BigDecimal calcularDescuento(@PathVariable("idCliente") Long idCliente, @PathVariable("idProducto") Long idProducto){
        return productoPedidoService.calculoDescuento(idCliente, idProducto);
    }*/

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{idPedido}/lista")
    public ResponseEntity<List<ItemPedidoDto>> productosPedidosPorPedido(@PathVariable("idPedido") long idPedido){
        List<ItemPedidoDto> lista = productoPedidoService.productosDePedido(idPedido);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
