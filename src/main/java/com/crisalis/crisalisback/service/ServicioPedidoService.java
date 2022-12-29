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

    public ServicioPedido agregarServicioPedido(ItemPedidoDto itemPedidoDto, Pedido pedido, Long id){
        ServicioPedido servicioPedido = new ServicioPedido();
        Servicio servicio = iServicio.findById(id).orElseThrow();
        servicioPedido.setPedido(pedido);
        servicioPedido.setProductoBase(servicio);
        servicioPedido.setCantidad(itemPedidoDto.getCantidad());
        double precioTotal = calculoPrecioTotal(servicio);
        System.out.println("El precio total mensual del servicio es de: " + precioTotal);
        servicioPedido.setPrecioFinalUnitario(precioTotal);
        return iServicioPedido.save(servicioPedido);
    }
    
    public double calculoPrecioTotal(Servicio servicio){
        double precioBase = servicio.getPrecioBase();
        double totalImpuestos = Adicional.impuestoIVA(precioBase) + Adicional.impuestoIIBB(servicio);
        double precioTotalUnitario = precioBase + totalImpuestos + servicio.getPrecioSoporte();

        return precioTotalUnitario;
    }

    public void borrarServicioPedido(Long idServicioPedido){

        iServicioPedido.deleteById(idServicioPedido);
    }

    
}
