package com.crisalis.crisalisback.service;

import com.crisalis.crisalisback.dto.EmpresaClienteDTO;
import com.crisalis.crisalisback.dto.PersonaClienteDTO;
import com.crisalis.crisalisback.model.EmpresaCliente;
import com.crisalis.crisalisback.model.PersonaCliente;
import com.crisalis.crisalisback.repository.IPersonaClienteRepository;
import com.crisalis.crisalisback.repository.IEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmpresaClienteService {
    private IEmpresa iEmpresa;
    private IPersonaClienteRepository iPersonaCliente;
    private PersonaClienteService personaClienteService;

    @Autowired
    public EmpresaClienteService(IEmpresa iEmpresa, IPersonaClienteRepository iPersonaCliente, PersonaClienteService personaClienteService) {
        this.iEmpresa = iEmpresa;
        this.iPersonaCliente = iPersonaCliente;
        this.personaClienteService = personaClienteService;
    }

    public EmpresaClienteDTO nuevaEmpresaCliente(EmpresaClienteDTO empresaClienteDTO){
        EmpresaCliente empresaCliente = EmpresaClienteDTO.dtoAEmpresaCliente(empresaClienteDTO);
        iEmpresa.save(empresaCliente);
        return empresaClienteDTO;
    }

    public EmpresaClienteDTO traerEmpresaCliente(Long cuit){
        EmpresaCliente empresaCliente = iEmpresa.findByCuit(cuit);
        return new EmpresaClienteDTO(empresaCliente);
    }

    public List<EmpresaClienteDTO> listaEmpresas(){
        return iEmpresa.findAll().stream()
                .map(EmpresaClienteDTO::new)
                .collect(Collectors.toList());
    }

    public EmpresaCliente editarClienteAsociado(Long idEmpresa, Long idCliente){
        PersonaCliente personaCliente = iPersonaCliente.findById(idCliente).orElseThrow();
        EmpresaCliente empresa = iEmpresa.findById(idEmpresa).orElseThrow();
        personaCliente.setEmpresaCliente(empresa);
        iPersonaCliente.save(personaCliente);
        return empresa;
    }

    public EmpresaClienteDTO editarEmpresa(Long cuitEmpresa, EmpresaClienteDTO empresaDTO){
        EmpresaCliente empresaOriginal = iEmpresa.findByCuit(cuitEmpresa);
        empresaOriginal.setCuit(empresaDTO.getCuit());
        empresaOriginal.setRazonSocial(empresaDTO.getRazonSocial());
        empresaOriginal.setFechaInicio(empresaDTO.getFechaInicio());
        iEmpresa.save(empresaOriginal);
        return empresaDTO;
    }

    public void eliminarEmpresa(Long cuitEmpresa){
        EmpresaCliente empresaCliente = iEmpresa.findByCuit(cuitEmpresa);
        if(empresaCliente.getPersonaCliente() != null){
            PersonaCliente personaCliente = empresaCliente.getPersonaCliente();
            personaCliente.eliminarEmpresaCliente();
        }
        iEmpresa.deleteByCuit(cuitEmpresa);
    }

    public PersonaClienteDTO mostrarPersonaAsociada(long cuitEmpresa){
        EmpresaCliente empresa = iEmpresa.findByCuit(cuitEmpresa);
        PersonaClienteDTO personaClienteDTO = null;
        if(empresa.getPersonaCliente() != null){
             personaClienteDTO = new PersonaClienteDTO(empresa.getPersonaCliente());
        }

        return personaClienteDTO;
    }


}
