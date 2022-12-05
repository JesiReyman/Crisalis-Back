package com.crisalis.crisalisback.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.crisalisback.model.Adicional;
import com.crisalis.crisalisback.model.ItemPedido;
import com.crisalis.crisalisback.model.Pedido;
import com.crisalis.crisalisback.model.Persona;
import com.crisalis.crisalisback.model.Producto;
import com.crisalis.crisalisback.model.ProductoPedido;
import com.crisalis.crisalisback.model.ServicioPedido;
import com.crisalis.crisalisback.repository.IPedidoRepositorio;
import com.crisalis.crisalisback.repository.IPersona;
import com.crisalis.crisalisback.repository.IServicioPedido;

@Service
@Transactional
public class PedidoService {
    private IPedidoRepositorio iPedido;
    private IPersona iPersona;
    private IServicioPedido iServicioPedido;
    
    @Autowired
    public PedidoService(IPedidoRepositorio iPedido, IPersona iPersona, IServicioPedido iServicioPedido) {
        this.iPedido = iPedido;
        this.iPersona = iPersona;
        this.iServicioPedido = iServicioPedido;
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
        //double descuento = 
        return null;
    }

    public Pedido detallePedido(Long idPedido){
        return iPedido.findById(idPedido).orElseThrow();
    }


    public void aplicarDescuentoAProducto(Long idPedido){
        Pedido pedido = iPedido.findById(idPedido).orElseThrow();
        List<ItemPedido> listaItemPedidos = pedido.getListaDeItems();
        //filtrar la lista para que solo contenga productos
        

    }

    public boolean tieneServicioActivo(Long idPersona){
        boolean tieneServicioActivo = false;
        List<Pedido> listaPedidos = listarPedidosPorPersonaFechaAsc(idPersona);
        //recorro la lista
        for(Pedido pedido : listaPedidos){
            //extraigo el id del pedido para luego buscar en la lista de servicios
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

        return tieneServicioActivo;
    }
}
