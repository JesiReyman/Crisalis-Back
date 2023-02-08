package com.crisalis.crisalisback.controller;

import java.util.List;

import com.crisalis.crisalisback.dto.ServicioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ServicioDTO> agregarServicio(@RequestBody ServicioDTO servicio) {
        ServicioDTO nuevoServicio = servicioService.agregarServicio(servicio);
        return new ResponseEntity<>(nuevoServicio, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lista")
    public ResponseEntity<List<ServicioDTO>> listarServicios() {
        List<ServicioDTO> listaDeServicios = servicioService.listarServicios();
        return new ResponseEntity<>(listaDeServicios, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{nombreServicio}")
    public ResponseEntity<ServicioDTO> traerServicio(@PathVariable("nombreServicio") String nombreServicio){
        ServicioDTO servicioDTO = servicioService.buscarServicio(nombreServicio);
        return new ResponseEntity<>(servicioDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("eliminar/{nombreServicio}")
    public void eliminarServicio(@PathVariable("nombreServicio") String nombreServicio){
        servicioService.eliminarServicio(nombreServicio);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("editar/{nombreServicio}")
    public ResponseEntity<ServicioDTO> editarServicio(@PathVariable("nombreServicio") String nombreServicio, @RequestBody ServicioDTO servicioDTO){
        ServicioDTO servicioEditado = servicioService.editarServicio(nombreServicio, servicioDTO);
        return new ResponseEntity<>(servicioEditado, HttpStatus.OK);
    }
}
