package com.api.apartamentos.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.api.apartamentos.models.ResenaModel;
import com.api.apartamentos.services.ResenaService;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {
    private final ResenaService service;
    public ResenaController(ResenaService service) { this.service = service; }

    @GetMapping public List<ResenaModel> all() { return service.findAll(); }

    @GetMapping("/{id}") public ResponseEntity<ResenaModel> one(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/reserva/{idReserva}") public List<ResenaModel> byReservation(@PathVariable Long idReserva) {
        return service.findByReserva(idReserva);
    }

    @PostMapping public ResponseEntity<ResenaModel> create(@Valid @RequestBody ResenaModel value) {
        value.setIdResena(null);
        return ResponseEntity.status(201).body(service.save(value));
    }

    // Solo se pueden editar las calificaciones y el comentario; reserva, cliente y fecha no cambian.
    @PutMapping("/{id}") public ResponseEntity<ResenaModel> update(@PathVariable Long id, @Valid @RequestBody ResenaModel value) {
        return service.findById(id).map(existing -> {
            existing.setCalificacionGeneral(value.getCalificacionGeneral());
            existing.setCalificacionComunicacion(value.getCalificacionComunicacion());
            existing.setComentario(value.getComentario());
            return ResponseEntity.ok(service.save(existing));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}