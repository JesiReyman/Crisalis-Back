package com.crisalis.crisalisback.service;

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
    private IPedidoRepositorio iPedido;
    private IProducto iProducto;
    private PedidoService pedidoService;
    private ProductoService productoService;


    @Autowired
    public ProductoPedidoService(IProductoPedido iProductoPedido, IPedidoRepositorio iPedido, IProducto iProducto) {
        this.iProductoPedido = iProductoPedido;
        this.iPedido = iPedido;
        this.iProducto = iProducto;
    }

    public ProductoPedido agregarProductoPedido(ItemPedidoDto itemPedidoDto, Pedido pedido, Long idProducto){
        //Producto producto = iProducto.findById(idProducto).orElseThrow();
        Producto producto = productoService.traerProducto(idProducto);
        ProductoPedido productoPedido = new ProductoPedido();
        productoPedido.setProductoBase(producto);
        productoPedido.setPedido(pedido);
        int aniosGarantia = itemPedidoDto.getAniosDeGarantia();
        productoPedido.setAniosDeGarantia(aniosGarantia);
        int cantidad = itemPedidoDto.getCantidad();
        productoPedido.setCantidad(cantidad);
        productoService.restarStock(producto, cantidad);


        double precioBase = producto.getPrecioBase();

        double precioTotal = calculoPrecioTotal(precioBase, aniosGarantia);
        productoPedido.setPrecioFinalUnitario(precioTotal);
        
        return iProductoPedido.save(productoPedido);
    }


    public double calculoPrecioTotal(double precio, int aniosGarantia){
        double impuestoIVA = Adicional.impuestoIVA(precio);
        double garantia = Adicional.cargoGarantia(precio, aniosGarantia);
        return precio + impuestoIVA + garantia;
    }

    public void borrarProductoPedido(Long idProductoPedido){
        iProductoPedido.deleteById(idProductoPedido);
    }

    public double calculoDescuento(Long idCliente, Long idProducto){
        Producto producto = iProducto.findById(idProducto).orElseThrow();
        ServicioPedido servicioActivo = pedidoService.servicioActivo(idCliente);
        double descuento = 0;
        if(servicioActivo.getPedido() != null){
            double precioBase = producto.getPrecioBase();
            descuento = Adicional.descuentoProducto(precioBase);
        }
        return descuento;
    }

}
