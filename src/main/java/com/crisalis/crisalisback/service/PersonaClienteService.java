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

    @Autowired
    public PersonaClienteService(IPersonaClienteRepository iPersonaClienteRepository, IEmpresa iEmpresa) {
        this.iPersonaClienteRepository = iPersonaClienteRepository;
        this.iEmpresa = iEmpresa;
    }

    public PersonaClienteDTO agregarCliente(PersonaClienteDTO personaClienteDTO){
        PersonaCliente personaCliente = PersonaClienteDTO.dtoAPersonaCliente(personaClienteDTO);
        iPersonaClienteRepository.save(personaCliente);
        return personaClienteDTO;
    }

    public List<PersonaClienteDTO> listaClientes(){
        return iPersonaClienteRepository.findAll().stream()
                .map(PersonaClienteDTO::new)
                .collect(Collectors.toList());
    }

    public Cliente traerCliente(Long idCliente){
        return iPersonaClienteRepository.findById(idCliente).orElseThrow();
    }

    public PersonaClienteDTO buscarClientePorDni(int dni){
        PersonaCliente personaCliente = iPersonaClienteRepository.findByDni(dni);
        return new PersonaClienteDTO(personaCliente);
    }

    public PersonaCliente buscarPorDNI(int dni){
        return iPersonaClienteRepository.findByDni(dni);
    }

    public PersonaClienteDTO editarCliente(int dniCliente, PersonaClienteDTO personaClienteDTO){
        PersonaCliente cliente = iPersonaClienteRepository.findByDni(dniCliente);
        cliente.setNombre(personaClienteDTO.getNombre());
        cliente.setApellido(personaClienteDTO.getApellido());
        cliente.setDni(personaClienteDTO.getDni());
        iPersonaClienteRepository.save(cliente);
        return personaClienteDTO;
    }

    public void eliminarCliente(int dniCLiente){
        iPersonaClienteRepository.deleteByDni(dniCLiente);
    }

    public void asociarAEmpresa(Long cuitEmpresa, int dniPersona){
        PersonaCliente personaCliente = iPersonaClienteRepository.findByDni(dniPersona);
        EmpresaCliente empresaCliente = iEmpresa.findByCuit(cuitEmpresa);
        personaCliente.setEmpresaCliente(empresaCliente);
        iPersonaClienteRepository.save(personaCliente);
    }
}
