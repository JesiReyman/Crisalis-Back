package com.crisalis.crisalisback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.crisalisback.model.Persona;
import com.crisalis.crisalisback.service.PersonaService;

@RestController
@RequestMapping("/persona")
public class PersonaController {
    private PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Persona> agregarPersona(@RequestBody Persona persona) {
        Persona nuevaPersona = personaService.agregarPersona(persona);
        return new ResponseEntity<>(nuevaPersona, HttpStatus.OK);
    }
}
