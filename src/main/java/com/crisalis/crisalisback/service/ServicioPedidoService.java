package com.crisalis.crisalisback.service;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.crisalisback.model.Adicional;
import com.crisalis.crisalisback.model.Pedido;
import com.crisalis.crisalisback.model.Producto;
import com.crisalis.crisalisback.model.Servicio;
import com.crisalis.crisalisback.model.ServicioPedido;
import com.crisalis.crisalisback.repository.IPedidoRepositorio;
import com.crisalis.crisalisback.repository.IServicio;
import com.crisalis.crisalisback.repository.IServicioPedido;

import java.math.BigDecimal;

@Service
@Transactional
public class ServicioPedidoService {
    private IServicioPedido iServicioPedido;
    private IPedidoRepositorio iPedido;
    private IServicio iServicio;

    @Autowired
    public ServicioPedidoService(IServicioPedido iServicioPedido, IPedidoRepositorio iPedido, IServicio iServicio) {
        this.iServicioPedido = iServicioPedido;
        this.iPedido = iPedido;
        this.iServicio = iServicio;
    }

    public ServicioPedido agregarServicioPedido(ItemPedidoDto itemPedidoDto, Pedido pedido, String nombre){
        ServicioPedido servicioPedido = new ServicioPedido();
        Servicio servicio = iServicio.findByNombre(nombre).orElseThrow();
        servicioPedido.setPedido(pedido);
        servicioPedido.setProductoBase(servicio);
        servicioPedido.setCantidad(itemPedidoDto.getCantidad());
        //double precioTotal = calculoPrecioTotal(servicio);
        //System.out.println("El precio total mensual del servicio es de: " + precioTotal);
        //servicioPedido.setPrecioFinalUnitario(precioTotal);
        return iServicioPedido.save(servicioPedido);
    }
    
    public BigDecimal calculoPrecioTotal(BigDecimal precioBase, BigDecimal totalImpuestos, String nombreServicio){
        Servicio servicio = iServicio.findByNombre(nombreServicio).orElseThrow();
        BigDecimal cargoSoporte = servicio.getPrecioSoporte();
        return precioBase.add(totalImpuestos).add(cargoSoporte);
    }

    public void borrarServicioPedido(Long idServicioPedido){

        iServicioPedido.deleteById(idServicioPedido);
    }

    
}
