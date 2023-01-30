package com.crisalis.crisalisback.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.Exception.ApiExceptionHandler;
import com.crisalis.crisalisback.dto.EstadoDTO;
import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.dto.PedidoDTO;
import com.crisalis.crisalisback.enums.EstadoDePedido;
import com.crisalis.crisalisback.model.*;
import com.crisalis.crisalisback.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PedidoService {
    private final IPedidoRepositorio iPedido;

    private final IServicioPedido iServicioPedido;

    private final IProductoPedido iProductoPedido;
    private EmpresaClienteService empresaClienteService;
    private PersonaClienteService personaClienteService;
    private ClienteService clienteService;
    private ItemPedidoService itemPedidoService;



    @Autowired
    public PedidoService(IPedidoRepositorio iPedido,

                         IServicioPedido iServicioPedido,
                         IProductoPedido iProductoPedido,

                         ClienteService clienteService,
                         ItemPedidoService itemPedidoService) {
        this.iPedido = iPedido;

        this.iServicioPedido = iServicioPedido;
        this.iProductoPedido = iProductoPedido;

        this.clienteService = clienteService;
        this.itemPedidoService = itemPedidoService;
    }


    public Pedido crearPedido(long dniOCuitCLiente, List<ItemPedidoDto> listaItemsPedidos){
        Cliente cliente = clienteService.encontrarCliente(dniOCuitCLiente);
        Pedido pedido = new Pedido(cliente);
        List<ItemPedido> listaItems = itemPedidoService.setearItemPedido(listaItemsPedidos, pedido);
        pedido.setListaDeItems(listaItems);
        return iPedido.save(pedido);

    }


    public List<PedidoDTO> listarPedidos(){
        List<Pedido> listaPedidos = iPedido.findAll();
        List<PedidoDTO> listaPedidosDTO = new ArrayList<>();
        for (Pedido pedido : listaPedidos
        ) {
            long dniOCuit = pedido.getCliente().getDniOCuit();
            List<ItemPedido> listaItems = pedido.getListaDeItems();
            BigDecimal precioBase = BigDecimal.ZERO;
            BigDecimal totalImpuestos = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            for (ItemPedido item : listaItems
                 ) {
                 precioBase = precioBase.add(item.getPrecioBase()).multiply(new BigDecimal(item.getCantidad()) );
                 totalImpuestos = totalImpuestos.add(item.getTotalImpuestos()).multiply(new BigDecimal(item.getCantidad()));
                 total = total.add(item.getPrecioFinalUnitario()).multiply(new BigDecimal(item.getCantidad()));
            }
            PedidoDTO pedidoDTO = new PedidoDTO(pedido, dniOCuit, precioBase, totalImpuestos, total);
            listaPedidosDTO.add(pedidoDTO);
        }
        return listaPedidosDTO;
    }

    public List<Pedido> listarPedidosPorClienteFechaAsc(Long idPersona) {
        return iPedido.findByClienteIdOrderByFechaCreacionAsc(idPersona);
    }

    public void eliminarPedido(Long idPedido){
        iPedido.deleteById(idPedido);
    }

    public Pedido encontrarPedido(Long idPedido){
        return iPedido.findById(idPedido).orElseThrow();
    }

    public void cambiarEstado(long idPedido, EstadoDTO estadoDTO){
        Pedido pedido = encontrarPedido(idPedido);
        pedido.setEstado(estadoDTO.getEstado());
    }
    public BigDecimal calculoDescuento(Long idPedido){

        List<ProductoPedido> listaProductosPedidos = iProductoPedido.findByPedidoId(idPedido);
        BigDecimal descuento = BigDecimal.ZERO;
        for (ProductoPedido productoPedido : listaProductosPedidos){
            ProductoBase producto = productoPedido.getProductoBase();
            BigDecimal precio = producto.getPrecioBase();
            descuento = descuento.add(Adicional.descuentoProducto(precio));
        }
        return descuento;
    }

    /*public ServicioPedido servicioActivo(Long idPersona){
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
    }*/






}
