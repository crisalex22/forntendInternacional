package com.Internacional.Ejercicio.Internacional.service;

import com.Internacional.Ejercicio.Internacional.data.model.Cliente;
import com.Internacional.Ejercicio.Internacional.data.ClienteRepository;

import org.hibernate.engine.jdbc.env.internal.LobCreationLogging_.logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

    public List<Map<String, Object>> guardarClientes(List<Cliente> clientes) {
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Cliente cliente : clientes) {
            Map<String, Object> response = new HashMap<>();
            response.put("nombre", cliente.getNombre());
            response.put("apellido", cliente.getApellido());
            response.put("identificacion", cliente.getIdentificacion());
            response.put("edad", cliente.getEdad());
            response.put("fechaRegistro", cliente.getFechaRegistro());
            response.put("correo", cliente.getCorreo());

            try {
                validarFechaRegistro(cliente.getFechaRegistro());
                validarCorreo(cliente.getCorreo());
                cliente.setCuenta(generarCuenta());
                clienteRepository.save(cliente);
                response.put("cuenta", cliente.getCuenta());
                response.put("status", "OK");
            } catch (IllegalArgumentException e) {
                response.put("cuenta", "N/A");
                response.put("status", "ERROR");
                response.put("error", e.getMessage());
            }

            responseList.add(response);
        }
        return responseList;
    }

    private void validarFechaRegistro(String fechaRegistro) {
        if (fechaRegistro == null || fechaRegistro.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de registro no puede estar vacía.");
        }
    
        // Expresión regular para validar el formato YYYY-MM-DD
        String fechaRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile(fechaRegex);
        
        if (!pattern.matcher(fechaRegistro).matches()) {
            throw new IllegalArgumentException("Formato de fecha inválido. Debe estar en formato YYYY-MM-DD.");
        }
    
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaCliente = LocalDate.parse(fechaRegistro.trim(), formatter);
            LocalDate fechaActual = LocalDate.now();
    
            if (fechaCliente.isAfter(fechaActual)) {
                throw new IllegalArgumentException("La fecha de registro no puede ser mayor a la actual.");
            }
    
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fecha inválida. Debe ser una fecha real en formato YYYY-MM-DD.");
        }
    }

    private String generarCuenta() {
        Random random = new Random();
        int cuenta = 100000000 + random.nextInt(900000000); // Número de 9 dígitos
        return String.valueOf(cuenta);
    }

    private void validarCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío.");
        }
    
        // Expresión regular para validar formato de correo
        String correoRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(correoRegex);
    
        if (!pattern.matcher(correo).matches()) {
            throw new IllegalArgumentException("El correo no tiene un formato válido.");
        }
    }    
}