package com.api.apartamentos.controllers;

import com.api.apartamentos.models.ClienteModel;
import com.api.apartamentos.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteModel> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> getClienteById(@PathVariable Long id) {
        Optional<ClienteModel> cliente = clienteService.getClienteById(id);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteModel> createCliente(@Valid @RequestBody ClienteModel cliente) {
        if (cliente.getPassword() == null || cliente.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        cliente.setId(null);
        return ResponseEntity.status(201).body(clienteService.saveCliente(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteModel clienteDetails) {
        Optional<ClienteModel> clienteOptional = clienteService.getClienteById(id);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ClienteModel clienteToUpdate = clienteOptional.get();
        clienteToUpdate.setNombres(clienteDetails.getNombres());
        clienteToUpdate.setApellidos(clienteDetails.getApellidos());
        clienteToUpdate.setEmail(clienteDetails.getEmail());
        clienteToUpdate.setTipo(clienteDetails.getTipo());
        clienteToUpdate.setTelefono(clienteDetails.getTelefono());
        clienteToUpdate.setFechaNacimiento(clienteDetails.getFechaNacimiento());
        clienteToUpdate.setIne(clienteDetails.getIne());
        clienteToUpdate.setDireccion(clienteDetails.getDireccion());
        clienteToUpdate.setEstatus(clienteDetails.getEstatus());
        if (clienteDetails.getPassword() != null && !clienteDetails.getPassword().isBlank()) {
            clienteToUpdate.setPassword(clienteDetails.getPassword());
        }
        return ResponseEntity.ok(clienteService.saveCliente(clienteToUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        if (clienteService.getClienteById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}