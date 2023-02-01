package com.crisalis.crisalisback.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.dto.ProductoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.crisalisback.model.Producto;
import com.crisalis.crisalisback.repository.IProducto;

@Service
@Transactional
public class ProductoService {
    private IProducto iProducto;

    @Autowired
    public ProductoService(IProducto iProducto) {
        this.iProducto = iProducto;
    }

    public ProductoDTO agregarProducto(ProductoDTO productoDTO){
        Producto producto = new Producto(productoDTO);
        return iProducto.save(producto).productoAproductoDTO();
    }

    public List<ProductoDTO> listarProductos() {
        return iProducto.findAll().stream()
                .map(Producto::productoAproductoDTO)
                .collect(Collectors.toList());
    }

    public Producto traerProducto(Long idProducto){
        return iProducto.findById(idProducto).orElseThrow();
    }

    public ProductoDTO traerProductoDTOByNombre(String nombreProducto){
        Producto producto = iProducto.findByNombre(nombreProducto).orElseThrow();
        return producto.productoAproductoDTO();
    }

    public Producto traerProductoByNombre(String nombreProducto){
        return iProducto.findByNombre(nombreProducto).orElseThrow();
    }

    public void restarStock(Producto producto, int cantidad){
        int stock = producto.getStock();
        stock -= cantidad;
        producto.setStock(stock);
        iProducto.save(producto);
    }

    public void sumarAlStock(Producto producto, int cantidad){
        int stockActual = producto.getStock();
        stockActual += cantidad;
        producto.setStock(stockActual);
    }

    public void eliminarProducto(Long idProducto){
        iProducto.deleteById(idProducto);
    }

    public void eliminarProductoPorNombre(String nombreProducto){
        iProducto.deleteByNombre(nombreProducto);
    }

    public ProductoDTO editarProducto(String nombreProducto, ProductoDTO productoDTO){
        Producto productoAEditar = traerProductoByNombre(nombreProducto);
        productoAEditar.setNombre(productoDTO.getNombre());
        productoAEditar.setDescripcion(productoDTO.getDescripcion());
        productoAEditar.setPrecioBase(productoDTO.getPrecioBase());
        productoAEditar.setStock(productoDTO.getStock());
        return iProducto.save(productoAEditar).productoAproductoDTO();
    }
}
