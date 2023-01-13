package com.crisalis.crisalisback.repository;

import com.crisalis.crisalisback.model.PersonaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonaClienteRepository extends JpaRepository<PersonaCliente, Long>{
    public PersonaCliente findByDni(int dni);
    public void deleteByDni(int dni);
}
