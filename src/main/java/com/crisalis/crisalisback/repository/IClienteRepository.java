package com.crisalis.crisalisback.repository;

import com.crisalis.crisalisback.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.Persona;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{

    
}
