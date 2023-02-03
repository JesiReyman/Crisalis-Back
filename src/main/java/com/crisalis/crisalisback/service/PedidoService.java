package com.crisalis.crisalisback.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.dto.EstadoDTO;
import com.crisalis.crisalisback.dto.ItemPedidoDto;
import com.crisalis.crisalisback.dto.PedidoDTO;
import com.crisalis.crisalisback.enums.EstadoDePedido;
import com.crisalis.crisalisback.model.*;
import com.crisalis.crisalisback.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.stereotype.Service;
import static org.apache.commons.lang3.compare.ComparableUtils.is;


@Service
@Transactional
public class PedidoService {
    private final IPedidoRepositorio iPedido;
    private ClienteService clienteService;
    private ItemPedidoService itemPedidoService;
    private DescuentoService descuentoService;



    @Autowired
    public PedidoService(IPedidoRepositorio iPedido,
                         ClienteService clienteService,
                         ItemPedidoService itemPedidoService,
                         DescuentoService descuentoService) {
        this.iPedido = iPedido;
        this.clienteService = clienteService;
        this.itemPedidoService = itemPedidoService;
        this.descuentoService = descuentoService;
    }


    public Pedido crearPedido(long dniOCuitCLiente, List<ItemPedidoDto> listaItemsPedidos){
        Cliente cliente = clienteService.encontrarCliente(dniOCuitCLiente);
        Pedido pedido = new Pedido(cliente);
        List<ItemPedido> listaItems = itemPedidoService.setearItemPedido(listaItemsPedidos, pedido, cliente.getDniOCuit());
        pedido.setListaDeItems(listaItems);
        ServicioPedido servicioActivo = itemPedidoService.buscarServicioActivo(cliente.getId());
        pedido = iPedido.save(pedido);
        if(servicioActivo != null){
            BigDecimal descuentoGenerado = itemPedidoService.calculoDescuentoTotal(listaItems);
            Descuento descuento = new Descuento();
            descuento.setDescuentoGenerado(descuentoGenerado);
            descuento.setServicioPedido(servicioActivo);

            descuento.setPedido(pedido);

            descuentoService.guardarDescuento(descuento);
            //pedido.setDescuento(descuento);

        }

        return pedido;
    }


    public List<PedidoDTO> listarPedidos(){
        List<Pedido> listaPedidos = iPedido.findAll(Sort.by(Sort.Direction.DESC, "fechaCreacion"));
        List<PedidoDTO> listaPedidosDTO = new ArrayList<>();
        for (Pedido pedido : listaPedidos
        ) {
            long dniOCuit = pedido.getCliente().getDniOCuit();
            List<ItemPedido> listaItems = pedido.getListaDeItems();
            BigDecimal precioBase = BigDecimal.ZERO;
            BigDecimal totalImpuestos = BigDecimal.ZERO;
            BigDecimal totalAdicionales = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            for (ItemPedido item : listaItems
                 ) {
                 precioBase = precioBase.add(item.getPrecioBase()).multiply(new BigDecimal(item.getCantidad()) );
                 totalImpuestos = totalImpuestos.add(item.getTotalImpuestos()).multiply(new BigDecimal(item.getCantidad()));
                 totalAdicionales = totalAdicionales.add(item.getTotalAdicionales()).multiply(new BigDecimal(item.getCantidad()));
                 total = total.add(item.getPrecioFinalUnitario()).multiply(new BigDecimal(item.getCantidad()));
            }
            Descuento descuento = descuentoService.buscarDescuento(pedido.getId());
            BigDecimal descuentoGenerado = BigDecimal.ZERO;
            if(descuento != null){
                descuentoGenerado = descuentoGenerado.add(descuento.getDescuentoGenerado());
            }

            PedidoDTO pedidoDTO = new PedidoDTO(pedido, dniOCuit, precioBase, totalImpuestos, totalAdicionales, total, descuentoGenerado);
            listaPedidosDTO.add(pedidoDTO);
        }
        return listaPedidosDTO;
    }

    public List<Pedido> listarPedidosPorClienteFechaDesc(Long idPersona) {
        return iPedido.findByClienteIdOrderByFechaCreacionDesc(idPersona);
    }

    public void eliminarPedido(Long idPedido){
        iPedido.deleteById(idPedido);
    }

    public Pedido encontrarPedido(Long idPedido){
        return iPedido.findById(idPedido).orElseThrow();
    }

    public void cambiarEstado(long idPedido, EstadoDTO estadoDTO){
        Pedido pedido = encontrarPedido(idPedido);
        if(pedido.getEstado().equals(EstadoDePedido.PENDIENTE)){
            if (estadoDTO.getEstado().equals(EstadoDePedido.CANCELADO)){
                itemPedidoService.reponerProductos(idPedido);
            }
            pedido.setEstado(estadoDTO.getEstado());
        } else{
            System.out.println("no se puede cambiar el estado");
        }
    }

    public Pedido editarPedido(long idPedido, List<ItemPedidoDto> listaItems){
        Pedido pedido = encontrarPedido(idPedido);
        Cliente cliente = pedido.getCliente();
        pedido.getListaDeItems().clear();
        List<ItemPedido> itemsPedidos = itemPedidoService.setearItemPedido(listaItems, pedido, cliente.getDniOCuit());
        pedido.getListaDeItems().addAll(itemsPedidos);
        return pedido;
    }


}
