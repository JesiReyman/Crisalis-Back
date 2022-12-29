package com.crisalis.crisalisback.service;

import com.crisalis.crisalisback.model.Cliente;
import com.crisalis.crisalisback.model.EmpresaCliente;
import com.crisalis.crisalisback.repository.IClienteRepository;
import com.crisalis.crisalisback.repository.IEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EmpresaClienteService {
    private IEmpresa iEmpresa;
    private IClienteRepository iPersonaCliente;

    @Autowired
    public EmpresaClienteService(IEmpresa iEmpresa, IClienteRepository iPersonaCliente) {
        this.iEmpresa = iEmpresa;
        this.iPersonaCliente = iPersonaCliente;
    }

    public EmpresaCliente nuevaEmpresaCliente(Long idClientePersona, EmpresaCliente empresaCliente){
        Cliente personaCliente = iPersonaCliente.findById(idClientePersona).orElseThrow();
        personaCliente.setEmpresaCliente(empresaCliente);
        iPersonaCliente.save(personaCliente);
        //empresaCliente.setCliente(personaCliente);

        return empresaCliente;
    }

    public EmpresaCliente trearEmpresaCliente(Long id){
        return iEmpresa.findById(id).orElseThrow();
    }

    public EmpresaCliente editarClienteAsociado(Long idEmpresa, Long idCliente){
        Cliente cliente = iPersonaCliente.findById(idCliente).orElseThrow();
        EmpresaCliente empresa = iEmpresa.findById(idEmpresa).orElseThrow();
        cliente.setEmpresaCliente(empresa);
        iPersonaCliente.save(cliente);
        return empresa;
    }

    public EmpresaCliente editarEmpresa(Long idEmpresa, EmpresaCliente empresa){
        EmpresaCliente empresaOriginal = iEmpresa.findById(idEmpresa).orElseThrow();
        empresaOriginal.setCuit(empresa.getCuit());
        empresaOriginal.setRazonSocial(empresa.getRazonSocial());
        empresaOriginal.setFechaInicio(empresa.getFechaInicio());
        return iEmpresa.save(empresaOriginal);
    }
}
