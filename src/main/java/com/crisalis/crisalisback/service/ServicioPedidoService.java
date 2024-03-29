package com.crisalis.crisalisback.service;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.model.ProductoPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.crisalisback.model.Pedido;
import com.crisalis.crisalisback.model.Servicio;
import com.crisalis.crisalisback.model.ServicioPedido;
import com.crisalis.crisalisback.repository.IPedidoRepositorio;
import com.crisalis.crisalisback.repository.IServicioPedido;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioPedidoService {
    private IServicioPedido iServicioPedido;

    private ServicioService servicioService;

    @Autowired
    public ServicioPedidoService(IServicioPedido iServicioPedido, ServicioService servicioService) {
        this.iServicioPedido = iServicioPedido;
        this.servicioService = servicioService;
    }

    public Servicio buscarServicioAsociado(String nombreServicio){
        return servicioService.encontrarServicio(nombreServicio);
    }

    public ServicioPedido  buscarServicioPedido(long idServicioPedido){
        return iServicioPedido.findById(idServicioPedido).orElseThrow();
    }

    
    public BigDecimal calculoPrecioTotal(BigDecimal precioBase, BigDecimal totalImpuestos, String nombreServicio){
        Servicio servicio = buscarServicioAsociado(nombreServicio);
        BigDecimal cargoSoporte = servicio.getPrecioSoporte();
        return precioBase.add(totalImpuestos).add(cargoSoporte);
    }

    public void borrarServicioPedido(Long idServicioPedido){

        iServicioPedido.deleteById(idServicioPedido);
    }

    public ServicioPedido cambiarEstadoServicio(String nombreServicio, Long idPedido, boolean activo){
        Servicio servicio = buscarServicioAsociado(nombreServicio);
        ServicioPedido servicioPedido = iServicioPedido.findByProductoBaseIdAndPedidoId(servicio.getId(), idPedido).orElseThrow();
        servicioPedido.setActivo(activo);
        return iServicioPedido.save(servicioPedido);
    }

    public BigDecimal setAdicionalPrecioSoporte(String nombreServicio){
        Servicio servicio = buscarServicioAsociado(nombreServicio);
        return servicio.getPrecioSoporte();
    }

    public ServicioPedido buscarPorId(long idServicioPedido){
        return iServicioPedido.findById(idServicioPedido).orElseThrow();
    }

    public List<ServicioPedido> buscarPorIdPedido(long idPedido){
        return iServicioPedido.findByPedidoId(idPedido);
    }

    public List<ItemPedidoDto> serviciosDePedido(long idPedido){
        List<ServicioPedido> servicios = buscarPorIdPedido(idPedido);
        return servicios.stream().map(ItemPedidoDto::new).collect(Collectors.toList());
    }

    
}
