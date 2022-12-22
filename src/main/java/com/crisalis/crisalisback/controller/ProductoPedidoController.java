package com.crisalis.crisalisback.controller;

import com.crisalis.crisalisback.model.ProductoPedido;
import com.crisalis.crisalisback.model.ServicioPedido;
import com.crisalis.crisalisback.service.ProductoPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedirProducto")
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

    @DeleteMapping("borrar/{idProductoPedido}")
    public void eliminarProductoPedido(@PathVariable("idProductoPedido") Long idProductoPedido){
        productoPedidoService.borrarProductoPedido(idProductoPedido);
    }

    /*@PostMapping("{idProducto}")
    public ResponseEntity<ProductoPedido> agregar(@RequestBody ProductoPedido productoPedido, @PathVariable("idProducto") Long idProductoPedido){
        ProductoPedido producto =
    }*/

}
