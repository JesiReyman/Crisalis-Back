package com.crisalis.crisalisback.service;

import java.util.List;

import javax.transaction.Transactional;

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

    public Servicio agregarServicio(Servicio servicio){
        return iServicio.save(servicio);
    }

    public List<Servicio> listarServicios() {
        return iServicio.findAll();
    }
}
