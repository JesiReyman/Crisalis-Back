package com.crisalis.crisalisback.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.Exception.ApiExceptionHandler;
import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.dto.PedidoDTO;
import com.crisalis.crisalisback.model.*;
import com.crisalis.crisalisback.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PedidoService {
    private final IPedidoRepositorio iPedido;
    private final IPersonaClienteRepository iPersonaClienteRepository;
    private final IServicioPedido iServicioPedido;

    private final IProductoPedido iProductoPedido;
    private EmpresaClienteService empresaClienteService;
    private PersonaClienteService personaClienteService;
    private ClienteService clienteService;
    private ItemPedidoService itemPedidoService;



    @Autowired
    public PedidoService(IPedidoRepositorio iPedido,
                         IPersonaClienteRepository iPersonaClienteRepository,
                         IServicioPedido iServicioPedido,
                         IProductoPedido iProductoPedido,
                         PersonaClienteService personaClienteService,
                         ClienteService clienteService,
                         ItemPedidoService itemPedidoService) {
        this.iPedido = iPedido;
        this.iPersonaClienteRepository = iPersonaClienteRepository;
        this.iServicioPedido = iServicioPedido;
        this.iProductoPedido = iProductoPedido;
        this.personaClienteService = personaClienteService;
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

    public PedidoDTO simularPedido(List<ItemPedidoDto> listaItemsPedidos){
        PedidoDTO pedido = new PedidoDTO();
        BigDecimal totalPrecioBase = BigDecimal.ZERO;
        BigDecimal totalPrecio = BigDecimal.ZERO;
        BigDecimal totalImpuestos = BigDecimal.ZERO;
        for (ItemPedidoDto itemPedido:
             listaItemsPedidos) {
            BigDecimal totalPrecioBaseItem = itemPedido.getPrecioBase();

            totalPrecioBase = totalPrecioBase.add(itemPedido.getPrecioBase());
            totalImpuestos = totalImpuestos.add(itemPedido.getTotalImpuestos().multiply(new BigDecimal(itemPedido.getCantidad())));
            totalPrecio = totalPrecio.add(itemPedido.getPrecioFinalUnitario().multiply(new BigDecimal(itemPedido.getCantidad())));
        }
        pedido.setPrecioBase(totalPrecioBase);
        pedido.setTotalImpuestos(totalImpuestos);
        pedido.setTotal(totalPrecio);
        return pedido;
    }

    public List<Pedido> listarPedidos(){
        return iPedido.findAll();
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

    public BigDecimal aceptarPedido(Long idPedido){
        Pedido pedido = iPedido.findById(idPedido).orElseThrow();
        Cliente cliente = pedido.getCliente();
        List<ItemPedido> items = pedido.getListaDeItems();
        BigDecimal precioTotal = BigDecimal.ZERO;
        for (ItemPedido item : items){
            precioTotal = precioTotal.add(item.getPrecioFinalUnitario()) ;
        }

        BigDecimal descuentoCalculado = BigDecimal.ZERO;
        ServicioPedido servicioActivo = servicioActivo(cliente.getId());
        if (servicioActivo.getPedido() != null ){
            descuentoCalculado = calculoDescuento(idPedido);
            Descuento descuento = new Descuento();
            descuento.setDescuentoGenerado(descuentoCalculado);
            descuento.setPedido(pedido);
            descuento.setServicioPedido(servicioActivo);

        }

        BigDecimal precioFinalPedido = precioTotal.subtract(descuentoCalculado);

        return precioFinalPedido;
    }




}
