package com.crisalis.crisalisback.service;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.crisalisback.model.Persona;
import com.crisalis.crisalisback.repository.IClienteRepository;

@Service
@Transactional
public class ClienteService {
    private final IClienteRepository iClienteRepository;

    @Autowired
    public ClienteService(IClienteRepository iClienteRepository) {
        this.iClienteRepository = iClienteRepository;
    }

    public Cliente agregarCliente(Cliente cliente){
        return iClienteRepository.save(cliente);
    }

    public Cliente traerCliente(Long idCliente){
        return iClienteRepository.findById(idCliente).orElseThrow();
    }
}
