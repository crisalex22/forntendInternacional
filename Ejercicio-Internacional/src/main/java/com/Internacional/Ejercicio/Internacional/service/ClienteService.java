package com.Internacional.Ejercicio.Internacional.service;

import com.Internacional.Ejercicio.Internacional.data.model.Cliente;
import com.Internacional.Ejercicio.Internacional.data.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

    public Cliente guardarCliente(Cliente cliente) {
        cliente.setCuenta(generarCuenta()); // Generar cuenta de 9 dígitos
        return clienteRepository.save(cliente);
    }

    public List<Cliente> guardarClientes(List<Cliente> clientes) {
        clientes.forEach(cliente -> cliente.setCuenta(generarCuenta()));
        return clienteRepository.saveAll(clientes);
    }

    private String generarCuenta() {
        Random random = new Random();
        int cuenta = 100000000 + random.nextInt(900000000); // Número de 9 dígitos
        return String.valueOf(cuenta);
    }
}
