package com.crisalis.crisalisback.service;

import com.crisalis.crisalisback.model.Descuento;
import com.crisalis.crisalisback.repository.IDescuento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DescuentoService {
    private IDescuento iDescuento;

    @Autowired

    public DescuentoService(IDescuento iDescuento) {
        this.iDescuento = iDescuento;
    }

    public void guardarDescuento(Descuento descuento){
        iDescuento.save(descuento);
    }

    public Descuento buscarDescuento(long idPedido){
        return iDescuento.findByPedidoId(idPedido).orElse(null);
    }

    public void eliminarDescuento(Descuento descuento){
         iDescuento.delete(descuento);
    }
}
