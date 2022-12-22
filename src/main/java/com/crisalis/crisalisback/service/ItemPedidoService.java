package com.crisalis.crisalisback.service;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.model.*;
import com.crisalis.crisalisback.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ItemPedidoService {
    private final IItemPedidoRepository iItemPedidoRepository;
    private IPersona iPersona;
    private IProducto iProducto;
    private ProductoPedidoService productoPedidoService;
    private ServicioPedidoService servicioPedidoService;
    private PedidoService pedidoService;

    @Autowired
    public ItemPedidoService(IItemPedidoRepository iItemPedidoRepository,
                             IProductoPedido iProductoPedido,
                             IServicioPedido iServicioPedido, IPersona iPersona,
                             IProducto iProducto,
                             ProductoPedidoService productoPedidoService,
                             IPedidoRepositorio iPedidoRepositorio,
                             ServicioPedidoService servicioPedidoService,
                             PedidoService pedidoService) {
        this.iItemPedidoRepository = iItemPedidoRepository;
        this.iPersona = iPersona;
        this.iProducto = iProducto;
        this.productoPedidoService = productoPedidoService;
        this.servicioPedidoService = servicioPedidoService;
        this.pedidoService = pedidoService;
    }
    public List<ItemPedido> listaItemsPedidos(){
        return iItemPedidoRepository.findAll();
    }

    public List<ItemPedido> buscarPorTipo(String tipo){
        return iItemPedidoRepository.findByTipo(tipo);
    }

    public void guardarItemsPedidos(List<ItemPedidoDto> listaItems, Long idPersona){
        Persona persona = iPersona.findById(idPersona).orElseThrow();

        Pedido pedido = pedidoService.agregarPedidoAPersona(idPersona);

        for(ItemPedidoDto item: listaItems){
            long id = item.getId();
            Producto producto = iProducto.findById(id).orElseThrow();
            if (Objects.equals(producto.getTipo(), "servicio")){
                System.out.println("es un servicio");
                servicioPedidoService.agregarServicioPedido(item, pedido, id);

            } else {
                System.out.println("es un producto");
                productoPedidoService.agregarProductoPedido(item, pedido, id);
            }
        }
    }
}
