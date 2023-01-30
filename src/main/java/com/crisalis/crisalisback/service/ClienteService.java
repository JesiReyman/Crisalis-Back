package com.crisalis.crisalisback.service;

import com.crisalis.crisalisback.model.Cliente;
import com.crisalis.crisalisback.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ClienteService {
    private IClienteRepository iClienteRepository;

    @Autowired

    public ClienteService(IClienteRepository iClienteRepository) {
        this.iClienteRepository = iClienteRepository;
    }

    public Cliente encontrarCliente(long dniOCuit){
        return iClienteRepository.findByDniOCuit(dniOCuit).orElseThrow();
    }

    public boolean existeDniOCuit(long dniOCuit){
        return iClienteRepository.existsByDniOCuit(dniOCuit);
    }
}
