package com.crisalis.crisalisback.repository;

import com.crisalis.crisalisback.enums.TipoAdicional;
import com.crisalis.crisalisback.model.Adicional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdicionalRepository extends JpaRepository<Adicional, Long> {
    Optional<Adicional> findByNombre(String nombre);
    Optional<List<Adicional>> findByTipo(TipoAdicional tipo);
    boolean existsByNombre(String nombre);
    void deleteByNombre(String nombre);
}
