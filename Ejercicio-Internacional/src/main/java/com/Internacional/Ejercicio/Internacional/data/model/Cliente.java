package com.Internacional.Ejercicio.Internacional.data.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String apellido;
    private String identificacion;
    private int edad;
    private String fechaRegistro;
    private String correo;
    private String cuenta;
}
