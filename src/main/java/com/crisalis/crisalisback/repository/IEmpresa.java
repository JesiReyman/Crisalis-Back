package com.crisalis.crisalisback.repository;

import com.crisalis.crisalisback.model.PersonaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.EmpresaCliente;

import java.util.Optional;

@Repository
public interface IEmpresa extends JpaRepository<EmpresaCliente, Long>{
    Optional<EmpresaCliente>  findByDniOCuit(long cuit);

    void deleteByDniOCuit(long cuit);
}
