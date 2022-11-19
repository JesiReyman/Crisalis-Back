package com.crisalis.crisalisback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.Persona;

@Repository
public interface IPersona extends JpaRepository<Persona, Long>{

    
}
