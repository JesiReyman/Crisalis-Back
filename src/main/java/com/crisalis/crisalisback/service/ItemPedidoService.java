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

@Service
@Transactional
public class ItemPedidoService {
    private final IItemPedidoRepository iItemPedidoRepository;
    private ProductoPedidoService productoPedidoService;
    private ServicioPedidoService servicioPedidoService;
    private ProductoBaseService productoBaseService;
    private ProductoService productoService;
    private ImpuestoService impuestoService;

    @Autowired
    public ItemPedidoService(IItemPedidoRepository iItemPedidoRepository,
                             ProductoBaseService productoBaseService,
                             ProductoPedidoService productoPedidoService,
                             ServicioPedidoService servicioPedidoService,
                             ImpuestoService impuestoService,
                             ProductoService productoService) {
        this.iItemPedidoRepository = iItemPedidoRepository;

        this.productoBaseService = productoBaseService;
        this.productoPedidoService = productoPedidoService;
        this.servicioPedidoService = servicioPedidoService;

        this.impuestoService = impuestoService;
        this.productoService = productoService;
    }
    public List<ItemPedido> listaItemsPedidos(){
        return iItemPedidoRepository.findAll();
    }

    public List<ItemPedido> buscarPorTipo(String tipo){
        return iItemPedidoRepository.findByTipo(tipo);
    }

    public List<ItemPedido> setearItemPedido(List<ItemPedidoDto> listaItemsPedidos, Pedido pedido){
        ItemPedido itemPedido;
        List<ItemPedido> listaItems = new ArrayList<ItemPedido>();

        for(ItemPedidoDto item : listaItemsPedidos){
            String nombreProductoBase = item.getNombre();
            ProductoBase productoBase = productoBaseService.encontrarProductoBase(nombreProductoBase);
            item = calcularItem(item);

            String tipo = productoBase.getTipo();

            if (tipo.equals("servicio")){
                System.out.println("es un servicio");
                itemPedido = ItemPedidoDto.dtoAServicioPedido(item);
            } else {
                System.out.println("es un producto");
                itemPedido = ItemPedidoDto.dtoAProductoPedido(item);
                productoPedidoService.actualizarStockProducto(nombreProductoBase, item.getCantidad());
            }
            itemPedido.setProductoBase(productoBase);
            itemPedido.setPedido(pedido);
            listaItems.add(itemPedido);
        }
        return listaItems;
    }

    public ItemPedidoDto calcularItem(ItemPedidoDto itemPedidoDto){
        //primero busco el producto base que agregué
        ProductoBase productoBase = productoBaseService.encontrarProductoBase(itemPedidoDto.getNombre());
        BigDecimal precioBase = productoBase.getPrecioBase();
        String tipo = productoBase.getTipo();

        //Llamo al servicio de impuestos, y calculo
        BigDecimal impuestoTotal = impuestoService.aplicarImpuesto(precioBase, tipo);

        //agrego los campos que me faltan al DTO
        itemPedidoDto.setPrecioBase(precioBase);
        itemPedidoDto.setTotalImpuestos(impuestoTotal);

        BigDecimal precioFinal = BigDecimal.ZERO;
        //si es un producto, tengo que calcular el precio por los años de garantia y sumarlos
        if (tipo.equals("producto")){
            precioFinal = productoPedidoService.calculoPrecioTotal(precioBase, impuestoTotal, itemPedidoDto.getAniosDeGarantia());


        } else if (tipo.equals("servicio")) {
            precioFinal = servicioPedidoService.calculoPrecioTotal(precioBase, impuestoTotal, itemPedidoDto.getNombre());
        }

        itemPedidoDto.setPrecioFinalUnitario(precioFinal);

        //llamo al servicio de descuento y lo aplico

        return itemPedidoDto;
    }




}
