package com.crisalis.crisalisback.controller;

import com.crisalis.crisalisback.dto.ImpuestoDTO;
import com.crisalis.crisalisback.service.ImpuestoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("impuesto")
public class ImpuestoController {
    private ImpuestoService impuestoService;

    public ImpuestoController(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    @PostMapping("nuevo")
    public ResponseEntity<ImpuestoDTO> crearImpuesto(@RequestBody ImpuestoDTO impuesto){
        ImpuestoDTO impuestoNuevo = impuestoService.crearImpuesto(impuesto);
        return new ResponseEntity<>(impuestoNuevo, HttpStatus.OK);
    }

    @PutMapping("editar/{nombreImpuesto}")
    public ResponseEntity<ImpuestoDTO> editarImpuesto(@PathVariable("nombreImpuesto") String nombreImpuesto, @RequestBody ImpuestoDTO impuestoDTO){
        ImpuestoDTO impuestoEditado = impuestoService.editarImpuesto(nombreImpuesto, impuestoDTO);
        return new ResponseEntity<>(impuestoEditado, HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{nombreImpuesto}")
    public void eliminarImpuesto(@PathVariable("nombreImpuesto") String nombreImpuesto){
        impuestoService.eliminarImpuesto(nombreImpuesto);
    }

    @GetMapping("lista")
    public List<ImpuestoDTO> listaImpuestos(){
        return impuestoService.obtenerListaImpuestos();
    }
}
