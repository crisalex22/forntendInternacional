package com.Internacional.Ejercicio.Internacional.data;

import org.springframework.stereotype.Repository;

import com.Internacional.Ejercicio.Internacional.data.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
