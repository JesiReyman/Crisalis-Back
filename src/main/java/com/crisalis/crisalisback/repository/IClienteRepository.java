package com.crisalis.crisalisback.repository;

import com.crisalis.crisalisback.model.Cliente;
import com.crisalis.crisalisback.model.EmpresaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByDniOCuit(long cuit);

    boolean existsByDniOCuit(long dniOCuit);
}
