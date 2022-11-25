package com.crisalis.crisalisback.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.crisalisback.model.Persona;
import com.crisalis.crisalisback.repository.IPersona;

@Service
@Transactional
public class PersonaService {
    private IPersona iPersona;

    @Autowired
    public PersonaService(IPersona iPersona) {
        this.iPersona = iPersona;
    }

    public Persona agregarPersona(Persona persona){
        return iPersona.save(persona);
    }
}
