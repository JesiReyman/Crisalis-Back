
package com.crisalis.crisalisback.security.repository;

import com.crisalis.crisalisback.security.entity.Rol;
import com.crisalis.crisalisback.security.enums.RolNombre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
