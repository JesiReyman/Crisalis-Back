package com.crisalis.crisalisback.controller;

import java.util.List;

import com.crisalis.crisalisback.dto.ProductoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProductoDTO> agregarProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO nuevoProducto = productoService.agregarProducto(productoDTO);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.OK);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> listaDeProductos = productoService.listarProductos();
        return new ResponseEntity<>(listaDeProductos, HttpStatus.OK);
    }

    /*@GetMapping("{idProducto}")
    public ResponseEntity<Producto> traerProducto(@PathVariable("idProducto") Long idProducto){
        Producto producto = productoService.traerProducto(idProducto);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }*/

    @GetMapping("traer/{nombreProducto}")
    public ResponseEntity<ProductoDTO> traerProductoPorNombre(@PathVariable("nombreProducto") String nombreProducto){
        ProductoDTO producto = productoService.traerProductoDTOByNombre(nombreProducto);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    /*@DeleteMapping("eliminar/{idProducto}")
    public void eliminarProducto(@PathVariable("idProducto") Long idProducto){
        productoService.eliminarProducto(idProducto);
    }*/
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("eliminar/{nombreProducto}")
    public void eliminarProducto(@PathVariable("nombreProducto") String nombreProducto){
        productoService.eliminarProductoPorNombre(nombreProducto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("editar/{nombreProducto}")
    public ResponseEntity<ProductoDTO> editarProducto(@PathVariable("nombreProducto") String nombreProducto, @RequestBody ProductoDTO productoDTO){
        ProductoDTO productoEditado = productoService.editarProducto(nombreProducto, productoDTO);
        return new ResponseEntity<>(productoEditado, HttpStatus.OK);
    }
}
