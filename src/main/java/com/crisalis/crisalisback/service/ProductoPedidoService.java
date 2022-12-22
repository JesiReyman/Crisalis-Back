package com.crisalis.crisalisback.service;

import java.util.List;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
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

    public ProductoPedido agregarProductoPedido(ItemPedidoDto itemPedidoDto, Pedido pedido, Long idProducto){
        Producto producto = iProducto.findById(idProducto).orElseThrow();
        ProductoPedido productoPedido = new ProductoPedido();
        productoPedido.setProducto(producto);
        productoPedido.setPedido(pedido);
        int aniosGarantia = itemPedidoDto.getAniosDeGarantia();
        productoPedido.setAniosDeGarantia(aniosGarantia);
        int cantidad = itemPedidoDto.getCantidad();
        productoPedido.setCantidad(cantidad);

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

    /*public ProductoPedido agregarProducto(ProductoPedido productoPedido, Long idProducto){
        Producto producto = iProducto.findById(idProducto).orElseThrow();
        productoPedido.setProducto(producto);
        iProductoPedido.sa
    }*/

}
