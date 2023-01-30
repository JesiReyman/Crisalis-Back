package com.crisalis.crisalisback.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.crisalisback.repository.IPedidoRepositorio;
import com.crisalis.crisalisback.repository.IProducto;
import com.crisalis.crisalisback.repository.IProductoPedido;
import com.crisalis.crisalisback.repository.IServicioPedido;

@Service
@Transactional
public class ProductoPedidoService {
    private IProductoPedido iProductoPedido;

    private IProducto iProducto;
    private PedidoService pedidoService;
    private ProductoService productoService;


    @Autowired
    public ProductoPedidoService(IProductoPedido iProductoPedido, IProducto iProducto, ProductoService productoService) {
        this.iProductoPedido = iProductoPedido;
        this.iProducto = iProducto;
        this.productoService = productoService;
    }

    public ProductoPedido agregarProductoPedido(ItemPedidoDto itemPedidoDto, String nombre){
        Producto producto = productoService.traerProductoByNombre(nombre);
        ProductoPedido productoPedido = new ProductoPedido();
        productoPedido.setProductoBase(producto);
        int aniosGarantia = itemPedidoDto.getAniosDeGarantia();
        productoPedido.setAniosDeGarantia(aniosGarantia);
        int cantidad = itemPedidoDto.getCantidad();
        productoPedido.setCantidad(cantidad);
        productoService.restarStock(producto, cantidad);

        BigDecimal precioBase = producto.getPrecioBase();
        productoPedido.setPrecioBase(precioBase);

        //double precioTotal = calculoPrecioTotal(precioBase, aniosGarantia);
        //productoPedido.setPrecioFinalUnitario(precioTotal);
        
        return iProductoPedido.save(productoPedido);
    }


    public BigDecimal calculoPrecioTotal(BigDecimal precioBase, BigDecimal precioImpuestos, int aniosGarantia){
        BigDecimal garantia = Adicional.cargoGarantia(precioBase, aniosGarantia);
        return precioBase.add(precioImpuestos).add(garantia);
    }

    public void borrarProductoPedido(Long idProductoPedido){
        iProductoPedido.deleteById(idProductoPedido);
    }

    /*public BigDecimal calculoDescuento(Long idCliente, Long idProducto){
        Producto producto = iProducto.findById(idProducto).orElseThrow();
        ServicioPedido servicioActivo = pedidoService.servicioActivo(idCliente);
        BigDecimal descuento = BigDecimal.ZERO;
        if(servicioActivo.getPedido() != null){
            BigDecimal precioBase = producto.getPrecioBase();
            descuento = Adicional.descuentoProducto(precioBase);
        }
        return descuento;
    }*/

    public Producto buscarProductoAsociado(String nombreProducto){
        return productoService.traerProductoByNombre(nombreProducto);
    }

    public void actualizarStockProducto(String nombreProducto, int cantidad){
        Producto producto = buscarProductoAsociado(nombreProducto);
        productoService.restarStock(producto, cantidad);
    }

}
