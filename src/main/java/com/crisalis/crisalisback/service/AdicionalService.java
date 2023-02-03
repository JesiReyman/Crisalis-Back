package com.crisalis.crisalisback.service;

import com.crisalis.crisalisback.dto.AdicionalDTO;
import com.crisalis.crisalisback.enums.AplicaAdicional;
import com.crisalis.crisalisback.enums.TipoAdicional;
import com.crisalis.crisalisback.model.Adicional;
import com.crisalis.crisalisback.repository.AdicionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdicionalService {
    private AdicionalRepository adicionalRepository;

    @Autowired
    public AdicionalService(AdicionalRepository adicionalRepository) {
        this.adicionalRepository = adicionalRepository;
    }

    public AdicionalDTO crearAdicional(AdicionalDTO adicionalDTO){
        Adicional adicional = AdicionalDTO.dtoAImpuesto(adicionalDTO);
        adicionalRepository.save(adicional);
        return adicionalDTO;
    }

    public AdicionalDTO editarAdicional(String nombreImpuesto, AdicionalDTO adicionalDTO){
        Adicional adicionalAEditar = adicionalRepository.findByNombre(nombreImpuesto).orElseThrow();
        Adicional adicionalEditado = AdicionalDTO.dtoAImpuesto(adicionalDTO);
        adicionalEditado.setId(adicionalAEditar.getId());
        adicionalRepository.save(adicionalEditado);
        return adicionalDTO;
    }

    public void eliminarAdicional(String nombreImpuesto){
        boolean existe = adicionalRepository.existsByNombre(nombreImpuesto);
        if(existe){
            adicionalRepository.deleteByNombre(nombreImpuesto);
        } else {
            System.out.println("El impuesto con ese nombre no existe");
        }

    }

    //candidato a caché
    public List<AdicionalDTO> obtenerListaImpuestos(){
        return adicionalRepository.findByTipo(TipoAdicional.IMPUESTO).orElseThrow()
                .stream()
                .map(AdicionalDTO:: new)
                .collect(Collectors.toList());

    }

    public List<AdicionalDTO> obtenerListaAdicionales(){
        return adicionalRepository.findByTipo(TipoAdicional.ADICIONAL).orElseThrow()
                .stream()
                .map(AdicionalDTO:: new)
                .collect(Collectors.toList());

    }

    public List<AdicionalDTO> obtenerListaDescuentos(){
        return adicionalRepository.findByTipo(TipoAdicional.DESCUENTO).orElseThrow()
                .stream()
                .map(AdicionalDTO:: new)
                .collect(Collectors.toList());

    }

    public List<AdicionalDTO> listarTodos(){
        return adicionalRepository.findAll()
                .stream()
                .map(AdicionalDTO::new)
                .collect(Collectors.toList());
    }

    public BigDecimal aplicarImpuesto(BigDecimal precioBase, String tipo){
        //aca se va a almacenar el calculo del impuesto
        BigDecimal valorCalculado = BigDecimal.ZERO;
        List<AdicionalDTO> listaImpuestos = obtenerListaImpuestos();

        for (AdicionalDTO adicionalDTO : listaImpuestos
             ) {
            if(adicionalDTO.getAplica().equals(AplicaAdicional.TODO) || adicionalDTO.getAplica().toString().equals(tipo.toUpperCase())){
                valorCalculado = valorCalculado.add(calculoImpuestoOAdicional(precioBase, adicionalDTO.getPorcentaje())) ;
            } else{
                System.out.println("no aplica");
            }

        }
        return valorCalculado;
    }

    public BigDecimal aplicarAdicionales(BigDecimal precioBase, String tipo, int aniosGarantia){
        //aca se va a almacenar el calculo del impuesto
        BigDecimal valorCalculado = BigDecimal.ZERO;
        List<AdicionalDTO> listaAdicionales = obtenerListaAdicionales();
        for (AdicionalDTO adicionalDTO : listaAdicionales
        ) {
            String nombreAdicional = adicionalDTO.getNombre();
            if(adicionalDTO.getAplica().equals(AplicaAdicional.TODO) || adicionalDTO.getAplica().toString().equals(tipo.toUpperCase())){
                if (nombreAdicional.contains("GARANTIA")){
                    BigDecimal calculoAdicional = calculoImpuestoOAdicional(precioBase, adicionalDTO.getPorcentaje())
                                                    .multiply(new BigDecimal(aniosGarantia));
                    valorCalculado = valorCalculado.add(calculoAdicional);
                } else {
                    valorCalculado = valorCalculado.add(calculoImpuestoOAdicional(precioBase, adicionalDTO.getPorcentaje())) ;
                }

            } else{
                System.out.println("no aplica");
            }
            System.out.println("el valor calculado es");
            System.out.println(valorCalculado);
        }
        return valorCalculado;
    }

    public BigDecimal calculoImpuestoOAdicional(BigDecimal precioBase, BigDecimal porcentaje){
        return precioBase.multiply(porcentaje);

    }

    public BigDecimal calcularDescuento(BigDecimal precioBase){
        List<AdicionalDTO> descuentos = obtenerListaDescuentos();
        BigDecimal descCalcPorItem = BigDecimal.ZERO;
        for (AdicionalDTO descuento : descuentos
             ) {
            descCalcPorItem = descCalcPorItem.add(calculoImpuestoOAdicional(precioBase, descuento.getPorcentaje()));
        }
                 ;
        return descCalcPorItem;
    }

}
