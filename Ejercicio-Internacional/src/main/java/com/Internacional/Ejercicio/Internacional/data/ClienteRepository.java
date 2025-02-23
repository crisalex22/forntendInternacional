package com.Internacional.Ejercicio.Internacional.data;

import org.springframework.stereotype.Repository;

import com.Internacional.Ejercicio.Internacional.data.model.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByIdentificacion(String identificacion);
}
