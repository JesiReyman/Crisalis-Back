package com.crisalis.crisalisback.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.model.*;
import com.crisalis.crisalisback.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PedidoService {
    private final IPedidoRepositorio iPedido;
    private final IClienteRepository iClienteRepository;
    private final IServicioPedido iServicioPedido;

    private final IProductoPedido iProductoPedido;
    private EmpresaClienteService empresaClienteService;


    @Autowired
    public PedidoService(IPedidoRepositorio iPedido,
                         IClienteRepository iClienteRepository,
                         IServicioPedido iServicioPedido,
                         IProductoPedido iProductoPedido) {
        this.iPedido = iPedido;
        this.iClienteRepository = iClienteRepository;
        this.iServicioPedido = iServicioPedido;
        this.iProductoPedido = iProductoPedido;
    }

    public Pedido agregarPedidoACliente(Cliente cliente){
        //Cliente cliente = iClienteRepository.findById(idCliente).orElseThrow();
        Pedido pedido = new Pedido(cliente);
        return iPedido.save(pedido);
    }

    public Pedido agregarPedidoACliente(EmpresaCliente empresaCliente, Pedido pedido){
        //Cliente cliente = iClienteRepository.findById(idCliente).orElseThrow();
        //EmpresaCliente empresaCliente = empresaClienteService.trearEmpresaCliente(idEmpresa);
        //Pedido pedido = new Pedido(cliente, empresaCliente);
        pedido.setEmpresaCliente(empresaCliente);
        return iPedido.save(pedido);
    }

    public List<Pedido> listarPedidosPorClienteFechaAsc(Long idPersona) {
        return iPedido.findByClienteIdOrderByFechaCreacionAsc(idPersona);
    }

    public void eliminarPedido(Long idPedido){
        iPedido.deleteById(idPedido);
    }



    public Pedido detallePedido(Long idPedido){
        return iPedido.findById(idPedido).orElseThrow();
    }


    public double calculoDescuento(Long idPedido){

        List<ProductoPedido> listaProductosPedidos = iProductoPedido.findByPedidoId(idPedido);
        double descuento = 0;
        for (ProductoPedido productoPedido : listaProductosPedidos){
            ProductoBase producto = productoPedido.getProductoBase();
            double precio = producto.getPrecioBase();
            descuento += Adicional.descuentoProducto(precio);
        }
        return descuento;
    }

    public ServicioPedido servicioActivo(Long idPersona){
        ServicioPedido servicioActivo = new ServicioPedido();
        List<Pedido> listaPedidos = listarPedidosPorClienteFechaAsc(idPersona);
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
        Cliente cliente = pedido.getCliente();
        List<ItemPedido> items = pedido.getListaDeItems();
        double precioTotal = 0;
        for (ItemPedido item : items){
            precioTotal += item.getPrecioFinalUnitario();
        }

        double descuentoCalculado = 0;
        ServicioPedido servicioActivo = servicioActivo(cliente.getId());
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
