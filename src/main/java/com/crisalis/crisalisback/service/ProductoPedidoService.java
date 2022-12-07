package com.crisalis.crisalisback.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.crisalisback.model.Adicional;
import com.crisalis.crisalisback.model.Pedido;
import com.crisalis.crisalisback.model.Producto;
import com.crisalis.crisalisback.model.ProductoPedido;
import com.crisalis.crisalisback.model.ServicioPedido;
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


    @Autowired
    public ProductoPedidoService(IProductoPedido iProductoPedido, IPedidoRepositorio iPedido, IProducto iProducto) {
        this.iProductoPedido = iProductoPedido;
        this.iPedido = iPedido;
        this.iProducto = iProducto;
    }

    public ProductoPedido agregarProductoPedido(ProductoPedido productoPedido, Long idPedido, Long idProducto){
        Producto producto = iProducto.findById(idProducto).orElseThrow();
        Pedido pedido = iPedido.findById(idPedido).orElseThrow();
        productoPedido.setProducto(producto);
        productoPedido.setPedido(pedido);

        double precioBase = producto.getPrecioBase();
        int aniosGarantia = productoPedido.getAniosDeGarantia();
        double precioTotal = calculoPrecioTotal(precioBase, aniosGarantia);
        productoPedido.setPrecioFinalUnitario(precioTotal);
        
        return iProductoPedido.save(productoPedido);
    }


    public double calculoPrecioTotal(double precio, int aniosGarantia){
        double impuestoIVA = Adicional.impuestoIVA(precio);
        double garantia = Adicional.cargoGarantia(precio, aniosGarantia);
        return precio + impuestoIVA + garantia;
    }

}
