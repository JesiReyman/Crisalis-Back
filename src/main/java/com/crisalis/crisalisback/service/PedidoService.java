package com.crisalis.crisalisback.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.model.*;
import com.crisalis.crisalisback.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PedidoService {
    private final IPedidoRepositorio iPedido;
    private final IPersona iPersona;
    private final IServicioPedido iServicioPedido;

    private final IProductoPedido iProductoPedido;


    @Autowired
    public PedidoService(IPedidoRepositorio iPedido,
                         IPersona iPersona,
                         IServicioPedido iServicioPedido,
                         IProductoPedido iProductoPedido) {
        this.iPedido = iPedido;
        this.iPersona = iPersona;
        this.iServicioPedido = iServicioPedido;
        this.iProductoPedido = iProductoPedido;
    }

    public Pedido agregarPedidoAPersona(Long idPersona){
        Persona persona = iPersona.findById(idPersona).orElseThrow();
        Pedido pedido = new Pedido(0, null, persona);
        return iPedido.save(pedido);
    }

    public List<Pedido> listarPedidosPorPersonaFechaAsc(Long idPersona) {
        return iPedido.findByPersonaIdOrderByFechaCreacionAsc(idPersona);
    }

    public Pedido crearPedido(int idPersona, List<Producto> listaProductos){

        return null;
    }

    public Pedido detallePedido(Long idPedido){
        return iPedido.findById(idPedido).orElseThrow();
    }


    public double calculoDescuento(Long idPedido){

        List<ProductoPedido> listaProductosPedidos = iProductoPedido.findByPedidoId(idPedido);
        double descuento = 0;
        for (ProductoPedido productoPedido : listaProductosPedidos){
            Producto producto = productoPedido.getProducto();
            double precio = producto.getPrecioBase();
            descuento += Adicional.descuentoProducto(precio);
        }
        return descuento;
    }

    public ServicioPedido servicioActivo(Long idPersona){
        ServicioPedido servicioActivo = new ServicioPedido();
        List<Pedido> listaPedidos = listarPedidosPorPersonaFechaAsc(idPersona);
        //recorro la lista
        for(Pedido pedido : listaPedidos){
            //extraigo el id del pedido para luego buscar en la lista de servicios
            Long id = pedido.getId();
            List <ServicioPedido> serviciosPedidos = iServicioPedido.findByPedidoId(id); 
            for (ServicioPedido servicioPedido : serviciosPedidos){
                boolean estaActivo = servicioPedido.isActivo();
                if(estaActivo){
                    servicioActivo = servicioPedido;
                    break;
                }
            }
        }
        return servicioActivo;
    }

    public double aceptarPedido(Long idPedido){
        Pedido pedido = iPedido.findById(idPedido).orElseThrow();
        Persona persona = pedido.getPersona();
        List<ItemPedido> items = pedido.getListaDeItems();
        double precioTotal = 0;
        for (ItemPedido item : items){
            precioTotal += item.getPrecioFinalUnitario();
        }

        double descuentoCalculado = 0;
        ServicioPedido servicioActivo = servicioActivo(persona.getId());
        if (servicioActivo.getPedido() != null ){
            descuentoCalculado = calculoDescuento(idPedido);
            Descuento descuento = new Descuento();
            descuento.setDescuentoGenerado(descuentoCalculado);
            descuento.setPedido(pedido);
            descuento.setServicioPedido(servicioActivo);

        }

        double precioFinalPedido = precioTotal - descuentoCalculado;

        return precioFinalPedido;
    }


}
