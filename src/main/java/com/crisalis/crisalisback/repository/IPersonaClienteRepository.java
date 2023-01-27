package com.crisalis.crisalisback.repository;

import com.crisalis.crisalisback.model.PersonaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPersonaClienteRepository extends JpaRepository<PersonaCliente, Long>{
    Optional<PersonaCliente>  findByDniOCuit(long dni);
    void deleteByDniOCuit(long dni);
}
