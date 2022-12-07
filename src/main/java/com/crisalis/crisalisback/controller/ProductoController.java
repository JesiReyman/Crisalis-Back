package com.crisalis.crisalisback.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.crisalisback.model.Producto;
import com.crisalis.crisalisback.service.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevo")
    public ResponseEntity<Producto> agregarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.agregarProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.OK);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> listaDeProductos = productoService.listarProductos();
        return new ResponseEntity<>(listaDeProductos, HttpStatus.OK);
    }
}
