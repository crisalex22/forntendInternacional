package com.Internacional.Ejercicio.Internacional.controller;

import com.Internacional.Ejercicio.Internacional.data.model.Cliente;
import com.Internacional.Ejercicio.Internacional.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
@CrossOrigin("*") // Permitir peticiones desde Angular
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> obtenerClientes() {
        return clienteService.obtenerClientes();
    }

    @PostMapping("/create")
    public ResponseEntity<List<Map<String, Object>>> guardarClientes(@RequestBody List<Cliente> clientes) {
        return ResponseEntity.ok(clienteService.guardarClientes(clientes));
    }
}
