package com.crisalis.crisalisback.service;

import com.crisalis.crisalisback.model.*;
import com.crisalis.crisalisback.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ItemPedidoService {
    private final IItemPedidoRepository iItemPedidoRepository;
    private IPersonaClienteRepository iPersonaClienteRepository;
    private IProductoBaseRepository iProductoBase;
    private ProductoPedidoService productoPedidoService;
    private ServicioPedidoService servicioPedidoService;
    private PedidoService pedidoService;
    private PersonaClienteService personaClienteService;

    @Autowired
    public ItemPedidoService(IItemPedidoRepository iItemPedidoRepository,
                             IProductoPedido iProductoPedido,
                             IServicioPedido iServicioPedido, IPersonaClienteRepository iPersonaClienteRepository,
                             IProductoBaseRepository iProductoBase,
                             ProductoPedidoService productoPedidoService,
                             IPedidoRepositorio iPedidoRepositorio,
                             ServicioPedidoService servicioPedidoService,
                             PedidoService pedidoService) {
        this.iItemPedidoRepository = iItemPedidoRepository;
        this.iPersonaClienteRepository = iPersonaClienteRepository;
        this.iProductoBase = iProductoBase;
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

    /*public void guardarItemsPedidos(List<ItemPedidoDto> listaItems, Long idCliente){
        Cliente cliente = clienteService.traerCliente(idCliente);
        Pedido pedido = pedidoService.agregarPedidoACliente(cliente);

        if(cliente.getEmpresa() != null){
            EmpresaCliente empresaCliente = cliente.getEmpresa();
            pedido.setEmpresaCliente(empresaCliente);
        }


        for(ItemPedidoDto item: listaItems){
            //long id = item.getId();
            String nombre = item.getNombre();
            //ProductoBase productoBase = iProductoBase.findById(id).orElseThrow();
            ProductoBase productoBase = iProductoBase.findBy(id).orElseThrow();
            if (Objects.equals(productoBase.getTipo(), "servicio")){
                System.out.println("es un servicio");
                servicioPedidoService.agregarServicioPedido(item, pedido, id);

            } else {
                System.out.println("es un producto");
                productoPedidoService.agregarProductoPedido(item, pedido, id);
            }
        }
    }*/
}
