package com.api.apartamentos.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.api.apartamentos.models.PropiedadImagenModel;
import com.api.apartamentos.services.PropiedadImagenService;

@RestController
@RequestMapping("/api/propiedad-imagenes")
public class PropiedadImagenController {
    private final PropiedadImagenService service;
    public PropiedadImagenController(PropiedadImagenService service) { this.service = service; }

    @GetMapping public List<PropiedadImagenModel> all() { return service.findAll(); }

    @GetMapping("/{id}") public ResponseEntity<PropiedadImagenModel> one(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/propiedad/{idPropiedad}") public List<PropiedadImagenModel> byProperty(@PathVariable Long idPropiedad) {
        return service.findByPropiedad(idPropiedad);
    }

    @PostMapping public ResponseEntity<PropiedadImagenModel> create(@Valid @RequestBody PropiedadImagenModel value) {
        value.setIdImagen(null);
        return ResponseEntity.status(201).body(service.save(value));
    }

    @PutMapping("/{id}") public ResponseEntity<PropiedadImagenModel> update(@PathVariable Long id, @Valid @RequestBody PropiedadImagenModel value) {
        return service.findById(id).map(existing -> {
            existing.setUrl(value.getUrl());
            existing.setDescripcion(value.getDescripcion());
            return ResponseEntity.ok(service.save(existing));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}