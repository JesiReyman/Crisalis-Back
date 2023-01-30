package com.crisalis.crisalisback.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.dto.ServicioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.crisalisback.model.Servicio;
import com.crisalis.crisalisback.repository.IServicio;


@Service
@Transactional
public class ServicioService {
    private IServicio iServicio;

    @Autowired
    public ServicioService(IServicio iServicio) {
        this.iServicio = iServicio;
    }

    public ServicioDTO agregarServicio(ServicioDTO servicioDTO){
        Servicio servicio = new Servicio(servicioDTO);
        return iServicio.save(servicio).servicioAservicioDTO();
    }

    public List<ServicioDTO> listarServicios() {
        return iServicio.findAll().stream()
                .map(Servicio::servicioAservicioDTO)
                .collect(Collectors.toList());
    }

    public void eliminarServicio(String nombre){
        iServicio.deleteByNombre(nombre);
    }

    public ServicioDTO editarServicio(String nombre, ServicioDTO servicioDTO){
        Servicio servicioAEditar = iServicio.findByNombre(nombre).orElseThrow();
        servicioAEditar.setNombre(servicioDTO.getNombre());
        servicioAEditar.setDescripcion(servicioDTO.getDescripcion());
        servicioAEditar.setPrecioBase(servicioDTO.getPrecioBase());
        servicioAEditar.setPrecioSoporte(servicioDTO.getPrecioSoporte());
        return iServicio.save(servicioAEditar).servicioAservicioDTO();
    }

    public ServicioDTO buscarServicio(String nombreServicio){
        Servicio servicio = iServicio.findByNombre(nombreServicio).orElseThrow();
        return servicio.servicioAservicioDTO();
    }

    public Servicio encontrarServicio(String nombre){
        return iServicio.findByNombre(nombre).orElseThrow();
    }


}
