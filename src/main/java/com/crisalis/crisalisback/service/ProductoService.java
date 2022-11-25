package com.crisalis.crisalisback.service;

import java.util.List;

import javax.transaction.Transactional;

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

    public Producto agregarProducto(Producto producto){
        return iProducto.save(producto);
    }

    public List<Producto> listarProductos() {
        return iProducto.findByTipo("producto");
    }
}
