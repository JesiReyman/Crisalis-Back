package com.crisalis.crisalisback.service;

import com.crisalis.crisalisback.dto.ImpuestoDTO;
import com.crisalis.crisalisback.model.Impuesto;
import com.crisalis.crisalisback.repository.ImpuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImpuestoService {
    private ImpuestoRepository impuestoRepository;
    private List<ImpuestoDTO> listaImpuestos;

    @Autowired
    public ImpuestoService(ImpuestoRepository impuestoRepository) {
        this.impuestoRepository = impuestoRepository;
        this.listaImpuestos = this.obtenerListaImpuestos();
    }

    public ImpuestoDTO crearImpuesto(ImpuestoDTO impuestoDTO){
        Impuesto impuesto = ImpuestoDTO.dtoAImpuesto(impuestoDTO);
        impuestoRepository.save(impuesto);
        return  impuestoDTO;
    }

    public ImpuestoDTO editarImpuesto(String nombreImpuesto, ImpuestoDTO impuestoDTO){
        Impuesto impuestoAEditar = impuestoRepository.findByNombre(nombreImpuesto).orElseThrow();
        Impuesto impuestoEditado = ImpuestoDTO.dtoAImpuesto(impuestoDTO);
        impuestoEditado.setId(impuestoAEditar.getId());
        impuestoRepository.save(impuestoEditado);
        return impuestoDTO;
    }

    public void eliminarImpuesto(String nombreImpuesto){
        boolean existe = impuestoRepository.existsByNombre(nombreImpuesto);
        if(existe){
            impuestoRepository.deleteByNombre(nombreImpuesto);
        } else {
            System.out.println("El impuesto con ese nombre no existe");
        }

    }

    //candidato a caché
    public List<ImpuestoDTO> obtenerListaImpuestos(){
        return impuestoRepository.findAll()
                .stream()
                .map(ImpuestoDTO :: new)
                .collect(Collectors.toList());

    }

    public BigDecimal aplicarImpuesto(BigDecimal precioBase, String tipo){
        //aca se va a almacenar el calculo del impuesto
        BigDecimal valorCalculado = BigDecimal.ZERO;
        for (ImpuestoDTO impuestoDTO : listaImpuestos
             ) {
            switch (impuestoDTO.getAplica()){
                case TODOS -> {
                    valorCalculado = valorCalculado.add(calculoImpuesto(precioBase, impuestoDTO.getPorcentaje())) ;
                }
                case PRODUCTOS -> {
                    if (tipo.equals("producto")) {
                        valorCalculado = valorCalculado.add(calculoImpuesto(precioBase, impuestoDTO.getPorcentaje()));
                    }
                }
                case SERVICIOS -> {
                    if (tipo.equals("servicio")) {
                        valorCalculado = valorCalculado.add(calculoImpuesto(precioBase, impuestoDTO.getPorcentaje()));
                    }
                }
            }
        }
        return valorCalculado;
    }

    public BigDecimal calculoImpuesto(BigDecimal precioBase,BigDecimal porcentaje){
        System.out.println("el calculo de impuesto da: " + precioBase.multiply(porcentaje));
        return precioBase.multiply(porcentaje);

    }




}
