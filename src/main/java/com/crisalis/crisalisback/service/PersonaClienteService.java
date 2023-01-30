package com.crisalis.crisalisback.service;

import javax.transaction.Transactional;

import com.crisalis.crisalisback.dto.PersonaClienteDTO;
import com.crisalis.crisalisback.model.Cliente;
import com.crisalis.crisalisback.model.EmpresaCliente;
import com.crisalis.crisalisback.model.PersonaCliente;
import com.crisalis.crisalisback.repository.IEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.crisalisback.repository.IPersonaClienteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonaClienteService {
    private final IPersonaClienteRepository iPersonaClienteRepository;
    private final IEmpresa iEmpresa;
    private ClienteService clienteService;

    @Autowired
    public PersonaClienteService(IPersonaClienteRepository iPersonaClienteRepository, IEmpresa iEmpresa, ClienteService clienteService) {
        this.iPersonaClienteRepository = iPersonaClienteRepository;
        this.iEmpresa = iEmpresa;
        this.clienteService = clienteService;
    }

    public PersonaClienteDTO agregarCliente(PersonaClienteDTO personaClienteDTO){
        PersonaCliente personaCliente = PersonaClienteDTO.dtoAPersonaCliente(personaClienteDTO);
        iPersonaClienteRepository.save(personaCliente);
        return personaClienteDTO;
    }

    public PersonaCliente registrarPersona(PersonaClienteDTO personaClienteDTO){
        PersonaCliente personaCliente = PersonaClienteDTO.dtoAPersonaCliente(personaClienteDTO);
        return iPersonaClienteRepository.save(personaCliente);
    }

    public boolean existeDni(long dni){
        return clienteService.existeDniOCuit(dni);
    }

    public List<PersonaClienteDTO> listaClientes(){
        return iPersonaClienteRepository.findAll().stream()
                .map(PersonaClienteDTO::new)
                .collect(Collectors.toList());
    }

    public Cliente traerCliente(Long idCliente){
        return iPersonaClienteRepository.findById(idCliente).orElseThrow();
    }

    public PersonaClienteDTO buscarClientePorDni(long dni){
        PersonaCliente personaCliente = iPersonaClienteRepository.findByDniOCuit(dni).orElseThrow();
        return new PersonaClienteDTO(personaCliente);
    }

    public PersonaCliente buscarPorDNI(long dni){
        return iPersonaClienteRepository.findByDniOCuit(dni).orElseThrow();
    }

    public PersonaClienteDTO editarCliente(long dniCliente, PersonaClienteDTO personaClienteDTO){
        PersonaCliente cliente = iPersonaClienteRepository.findByDniOCuit(dniCliente).orElseThrow();
        cliente.setNombre(personaClienteDTO.getNombre());
        cliente.setApellido(personaClienteDTO.getApellido());
        cliente.setDniOCuit(personaClienteDTO.getDniOCuit());
        iPersonaClienteRepository.save(cliente);
        return personaClienteDTO;
    }

    public void eliminarCliente(long dniCLiente){
        iPersonaClienteRepository.deleteByDniOCuit(dniCLiente);
    }

    public void asociarAEmpresa(long cuitEmpresa, long dniPersona){
        PersonaCliente personaCliente = iPersonaClienteRepository.findByDniOCuit(dniPersona).orElseThrow();
        EmpresaCliente empresaCliente = iEmpresa.findByDniOCuit(cuitEmpresa).orElseThrow();
        personaCliente.setEmpresaCliente(empresaCliente);
        iPersonaClienteRepository.save(personaCliente);
    }
}
