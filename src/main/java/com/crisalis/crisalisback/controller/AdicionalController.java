package com.crisalis.crisalisback.controller;

import com.crisalis.crisalisback.dto.AdicionalDTO;
import com.crisalis.crisalisback.service.AdicionalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("adicional")
public class AdicionalController {
    private AdicionalService adicionalService;

    public AdicionalController(AdicionalService adicionalService) {
        this.adicionalService = adicionalService;
    }

    @PostMapping("nuevo")
    public ResponseEntity<AdicionalDTO> crearImpuesto(@RequestBody AdicionalDTO impuesto){
        AdicionalDTO impuestoNuevo = adicionalService.crearAdicional(impuesto);
        return new ResponseEntity<>(impuestoNuevo, HttpStatus.OK);
    }

    @PutMapping("editar/{nombreAdicional}")
    public ResponseEntity<AdicionalDTO> editarImpuesto(@PathVariable("nombreAdicional") String nombreAdicional, @RequestBody AdicionalDTO adicionalDTO){
        AdicionalDTO impuestoEditado = adicionalService.editarAdicional(nombreAdicional, adicionalDTO);
        return new ResponseEntity<>(impuestoEditado, HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{nombreAdicional}")
    public void eliminarImpuesto(@PathVariable("nombreAdicional") String nombreAdicional){
        adicionalService.eliminarAdicional(nombreAdicional);
    }

    @GetMapping("lista")
    public List<AdicionalDTO> listaAdicionales(){
        return adicionalService.listarTodos();
    }
}
