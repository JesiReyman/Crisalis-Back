package com.crisalis.crisalisback.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.crisalisback.model.Servicio;
import com.crisalis.crisalisback.service.ServicioService;

@RestController
@RequestMapping("/servicio")
public class ServicioController {
    private ServicioService servicioService;


    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevo")
    public ResponseEntity<Servicio> agregarServicio(@RequestBody Servicio servicio) {
        Servicio nuevoServicio = servicioService.agregarServicio(servicio);
        return new ResponseEntity<>(nuevoServicio, HttpStatus.OK);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Servicio>> listarServicios() {
        List<Servicio> listaDeServicios = servicioService.listarServicios();
        return new ResponseEntity<>(listaDeServicios, HttpStatus.OK);
    }
}
