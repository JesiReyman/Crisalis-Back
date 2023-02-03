package com.crisalis.crisalisback.service;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.model.*;
import com.crisalis.crisalisback.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.compare.ComparableUtils.is;

@Service
@Transactional
public class ItemPedidoService {
    private final IItemPedidoRepository iItemPedidoRepository;
    private ProductoPedidoService productoPedidoService;
    private ServicioPedidoService servicioPedidoService;
    private ProductoBaseService productoBaseService;
    private ProductoService productoService;
    private AdicionalService adicionalService;
    private ClienteService clienteService;

    @Autowired
    public ItemPedidoService(IItemPedidoRepository iItemPedidoRepository,
                             ProductoBaseService productoBaseService,
                             ProductoPedidoService productoPedidoService,
                             ServicioPedidoService servicioPedidoService,
                             AdicionalService adicionalService,
                             ProductoService productoService,
                             ClienteService clienteService) {
        this.iItemPedidoRepository = iItemPedidoRepository;

        this.productoBaseService = productoBaseService;
        this.productoPedidoService = productoPedidoService;
        this.servicioPedidoService = servicioPedidoService;

        this.adicionalService = adicionalService;
        this.productoService = productoService;
        this.clienteService = clienteService;
    }
    public List<ItemPedido> listaItemsPedidos(){
        return iItemPedidoRepository.findAll();
    }
    public List<ItemPedido> buscarPorTipo(String tipo){
        return iItemPedidoRepository.findByTipo(tipo);
    }

    public List<ItemPedido> setearItemPedido(List<ItemPedidoDto> listaItemsPedidos, Pedido pedido, long dniOCuitCliente){
        ItemPedido itemPedido;
        List<ItemPedido> listaItems = new ArrayList<ItemPedido>();

        for(ItemPedidoDto item : listaItemsPedidos){
            String nombreProductoBase = item.getNombre();
            ProductoBase productoBase = productoBaseService.encontrarProductoBase(nombreProductoBase);
            item = calcularItem(item, dniOCuitCliente);

            String tipo = productoBase.getTipo();

            if (tipo.equals("servicio")){
                itemPedido = ItemPedidoDto.dtoAServicioPedido(item);
            } else {
                itemPedido = ItemPedidoDto.dtoAProductoPedido(item);
                productoPedidoService.actualizarStockProducto(nombreProductoBase, item.getCantidad());
            }
            itemPedido.setProductoBase(productoBase);
            itemPedido.setPedido(pedido);
            listaItems.add(itemPedido);
        }
        return listaItems;
    }

    public ItemPedidoDto calcularItem(ItemPedidoDto itemPedidoDto, long dniOCuitCLiente){
        //primero busco el producto base que agregué
        ProductoBase productoBase = productoBaseService.encontrarProductoBase(itemPedidoDto.getNombre());
        BigDecimal precioBase = productoBase.getPrecioBase();
        String tipo = productoBase.getTipo();
        int aniosGarantia = itemPedidoDto.getAniosDeGarantia();

        //Llamo al servicio de adicionales y les calculo el total de impuestos y adicionales aplicado a cada item
        BigDecimal impuestoTotal = adicionalService.aplicarImpuesto(precioBase, tipo);

        BigDecimal adicionalTotal = BigDecimal.ZERO;
        if (aniosGarantia != 0){
            System.out.println(precioBase);
             adicionalTotal = adicionalService.aplicarAdicionales(precioBase, tipo, aniosGarantia);
            System.out.println(adicionalTotal);
        } else {
            if(tipo.equals("servicio")){
                adicionalTotal = servicioPedidoService.setAdicionalPrecioSoporte(productoBase.getNombre());
            }
        }

        Cliente cliente = clienteService.encontrarCliente(dniOCuitCLiente);
        ServicioPedido servicioActivo = buscarServicioActivo(cliente.getId());
        BigDecimal descuentoItem = BigDecimal.ZERO;
        if (servicioActivo != null) {
             descuentoItem = adicionalService.calcularDescuento(precioBase); //.multiply(new BigDecimal(itemPedidoDto.getCantidad()) );

        }

        //agrego los campos que me faltan al DTO
        itemPedidoDto.setPrecioBase(precioBase);
        itemPedidoDto.setTotalImpuestos(impuestoTotal);
        itemPedidoDto.setTotalAdicionales(adicionalTotal);
        itemPedidoDto.setDescuento(descuentoItem);

        BigDecimal precioFinal = BigDecimal.ZERO;
        //si es un producto, tengo que calcular el precio por los años de garantia y sumarlos
        if (tipo.equals("producto")){
            precioFinal = productoPedidoService.calculoPrecioTotal(precioBase, impuestoTotal, adicionalTotal);

        } else if (tipo.equals("servicio")) {
            precioFinal = servicioPedidoService.calculoPrecioTotal(precioBase, impuestoTotal, itemPedidoDto.getNombre());
        }
        itemPedidoDto.setPrecioFinalUnitario(precioFinal);
        //llamo al servicio de descuento y lo aplico

        return itemPedidoDto;
    }


    public List<ItemPedidoDto> obtenerItemsDePedido(long idPedido){
        List<ProductoPedido> listaProductoPedido = productoPedidoService.buscarPorIdPedido(idPedido);
        List<ServicioPedido> listaServicioPedido = servicioPedidoService.buscarPorIdPedido(idPedido);
        List<ItemPedidoDto> listaItems = new ArrayList<>() ;
        for (ProductoPedido producto : listaProductoPedido
             ) {
            ItemPedidoDto itemDto = new ItemPedidoDto(producto);
            listaItems.add(itemDto);
        }
        for (ServicioPedido servicio : listaServicioPedido
             ) {
            ItemPedidoDto itemDto = new ItemPedidoDto(servicio);
            listaItems.add(itemDto);
        }
        return listaItems;

    }

    public List<ItemPedido> obtenerProductosDePedido(long idPedido){
        return iItemPedidoRepository.findByPedidoIdAndTipo(idPedido, "producto");
    }


    public void reponerProductos(long idPedido){
        List<ItemPedido> listaProductos = obtenerProductosDePedido(idPedido);
        productoPedidoService.restaurarStock(listaProductos);
    }

    public ServicioPedido buscarServicioActivo(long idCliente){
        ItemPedido itemActivo = iItemPedidoRepository.BUSCAR_SERVICIO_ACTIVO(idCliente).orElse(null);
        ServicioPedido servicioActivo = null;
        if(itemActivo != null){
             servicioActivo = servicioPedidoService.buscarServicioPedido(itemActivo.getId());
        }
       return servicioActivo;

    }

    public BigDecimal calculoDescuentoTotal(List<ItemPedido> listaItems) {
        List<ItemPedido> listaProductos = listaItems.stream()
                .filter(item -> item.getTipo().equals("producto"))
                .toList();
        BigDecimal descuentoCalculado = BigDecimal.ZERO;
        for (ItemPedido producto : listaProductos
             ) {
             descuentoCalculado = descuentoCalculado.add(adicionalService.calcularDescuento(producto.getPrecioBase()).multiply(new BigDecimal(producto.getCantidad()))) ;

        }
        BigDecimal descuentoTotal = BigDecimal.ZERO;
        if(is(descuentoCalculado).lessThanOrEqualTo(new BigDecimal(2500))){
            descuentoTotal = descuentoTotal.add(descuentoCalculado);
        } else {
            descuentoTotal = new BigDecimal(2500);
        }

        return descuentoTotal;
    }

}
