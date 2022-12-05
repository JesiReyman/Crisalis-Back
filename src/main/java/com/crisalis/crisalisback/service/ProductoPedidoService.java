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
    private IServicioPedido iServicioPedido;

    @Autowired
    public ProductoPedidoService(IProductoPedido iProductoPedido, IPedidoRepositorio iPedido, IProducto iProducto, IServicioPedido iServicioPedido) {
        this.iProductoPedido = iProductoPedido;
        this.iPedido = iPedido;
        this.iProducto = iProducto;
        this.iServicioPedido = iServicioPedido;
    }

    public ProductoPedido agregarProductoPedido(ProductoPedido productoPedido, Long idProducto, Long idPedido){
        Producto producto = iProducto.findById(idProducto).orElseThrow();
        Pedido pedido = iPedido.findById(idPedido).orElseThrow();
        productoPedido.setProducto(producto);
        productoPedido.setPedido(pedido);
        double precioBase = producto.getPrecioBase();
        double precioConIva = calculoPrecioTotal(precioBase);
        productoPedido.setPrecioFinalUnitario(precioConIva);
        
        
        return iProductoPedido.save(productoPedido);
    }

    public List<Pedido> encontrarPedidosPorPersona(Long idPersona, Long idPedido){
        List<Pedido> listaPedidos = iPedido.findByPersonaId(idPersona);
        listaPedidos.removeIf(pedido -> (pedido.getId() == idPedido));
        return listaPedidos;
    }

    public boolean tieneServicioActivo(List<Pedido> listaPedidos){
        boolean tieneServicioActivo = false;
        for(Pedido pedido : listaPedidos){
            Long id = pedido.getId();
            List <ServicioPedido> serviciosPedidos = iServicioPedido.findByPedidoId(id); 
            for (ServicioPedido servicioPedido : serviciosPedidos){
                boolean estaActivo = servicioPedido.isActivo();
                if(estaActivo){
                    tieneServicioActivo = !tieneServicioActivo;
                    break;
                }
            }
            
        }
        System.out.println(tieneServicioActivo);
        return tieneServicioActivo;
    }

    public double calculoPrecioTotal(double precio){
        double impuestoIVA = Adicional.impuestoIVA(precio);
        double precioTotal = precio + impuestoIVA;

        return precioTotal;
    }


    
}
