package com.crisalis.crisalisback.service;

import com.crisalis.crisalisback.dto.ClienteDTO;
import com.crisalis.crisalisback.model.Cliente;
import com.crisalis.crisalisback.model.EmpresaCliente;
import com.crisalis.crisalisback.model.PersonaCliente;
import com.crisalis.crisalisback.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ClienteService {
    private IClienteRepository iClienteRepository;
    //private PersonaClienteService personaClienteService;
    //private EmpresaClienteService empresaClienteService;

    @Autowired

    public ClienteService(IClienteRepository iClienteRepository) {
        this.iClienteRepository = iClienteRepository;
       // this.personaClienteService = personaClienteService;
      //  this.empresaClienteService = empresaClienteService;
    }

    public Cliente encontrarCliente(long dniOCuit){
        return iClienteRepository.findByDniOCuit(dniOCuit).orElseThrow();
    }

    public boolean existeDniOCuit(long dniOCuit){
        return iClienteRepository.existsByDniOCuit(dniOCuit);
    }

   /* public ClienteDTO obtenerCliente(long dniOCuit){
        Cliente cliente = encontrarCliente(dniOCuit);
        String tipo = cliente.getTipo();
        ClienteDTO clienteDTO = new ClienteDTO();
        if(tipo.equals("persona")){
            PersonaCliente persona = personaClienteService.buscarPorDNI(dniOCuit);
            clienteDTO = new ClienteDTO(persona);
        } else if (tipo.equals("empresa")) {
            EmpresaCliente empresa = empresaClienteService.encontrarEmpresa(dniOCuit);
            clienteDTO = new ClienteDTO(empresa);
        }
        return clienteDTO;
    }*/
}
