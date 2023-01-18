package com.crisalis.crisalisback.service;

import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.dto.PersonaClienteDTO;
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
    private IPersonaClienteRepository iPersonaClienteRepository;
    private IProductoBaseRepository iProductoBase;
    private ProductoPedidoService productoPedidoService;
    private ServicioPedidoService servicioPedidoService;
    private PedidoService pedidoService;
    private PersonaClienteService personaClienteService;
    //private final IPedidoRepositorio iPedidoRepositorio;

    @Autowired
    public ItemPedidoService(IItemPedidoRepository iItemPedidoRepository,
                             IProductoPedido iProductoPedido,
                             IServicioPedido iServicioPedido, IPersonaClienteRepository iPersonaClienteRepository,
                             IProductoBaseRepository iProductoBase,
                             ProductoPedidoService productoPedidoService,
                             IPedidoRepositorio iPedidoRepositorio,
                             ServicioPedidoService servicioPedidoService,
                             PedidoService pedidoService,
                             PersonaClienteService personaClienteService) {
        this.iItemPedidoRepository = iItemPedidoRepository;
        this.iPersonaClienteRepository = iPersonaClienteRepository;
        this.iProductoBase = iProductoBase;
        this.productoPedidoService = productoPedidoService;
        this.servicioPedidoService = servicioPedidoService;
        this.pedidoService = pedidoService;
        this.personaClienteService = personaClienteService;
    }
    public List<ItemPedido> listaItemsPedidos(){
        return iItemPedidoRepository.findAll();
    }

    public List<ItemPedido> buscarPorTipo(String tipo){
        return iItemPedidoRepository.findByTipo(tipo);
    }

    public void guardarItemsPedidos(List<ItemPedidoDto> listaItems, int idCliente){
        PersonaCliente personaCliente = personaClienteService.buscarPorDNI(idCliente) ;

        Pedido pedido = pedidoService.agregarPedidoACliente(personaCliente.getId());


        /*if(cliente.getEmpresa() != null){
            EmpresaCliente empresaCliente = cliente.getEmpresa();
            pedido.setEmpresaCliente(empresaCliente);
        }*/


        for(ItemPedidoDto item: listaItems){
            //long id = item.getId();
            String nombre = item.getNombre();
            //ProductoBase productoBase = iProductoBase.findBy(id).orElseThrow();
            ProductoBase productoBase = iProductoBase.findByNombre(nombre);
            if (Objects.equals(productoBase.getTipo(), "servicio")){
                System.out.println("es un servicio");
                servicioPedidoService.agregarServicioPedido(item, pedido, nombre);

            } else {
                System.out.println("es un producto");
                productoPedidoService.agregarProductoPedido(item, pedido, nombre);
            }
        }
    }
}
