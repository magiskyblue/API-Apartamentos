package com.api.apartamentos.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.api.apartamentos.models.ReservaModel;
import com.api.apartamentos.services.ReservaService;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    private final ReservaService service;
    public ReservaController(ReservaService service) { this.service = service; }

    @GetMapping public List<ReservaModel> all() { return service.findAll(); }

    @GetMapping("/{id}") public ResponseEntity<ReservaModel> one(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping public ResponseEntity<ReservaModel> create(@Valid @RequestBody ReservaModel value) {
        value.setIdReservacion(null);
        return ResponseEntity.status(201).body(service.save(value));
    }

    @PutMapping("/{id}") public ResponseEntity<ReservaModel> update(@PathVariable Long id, @Valid @RequestBody ReservaModel value) {
        return service.findById(id).map(existing -> {
            existing.setUnidad(value.getUnidad());
            existing.setCliente(value.getCliente());
            existing.setFechaEntrada(value.getFechaEntrada());
            existing.setFechaSalida(value.getFechaSalida());
            if (value.getFechaCheckin() != null) existing.setFechaCheckin(value.getFechaCheckin());
            if (value.getFechaCheckout() != null) existing.setFechaCheckout(value.getFechaCheckout());
            existing.setImporteTotal(value.getImporteTotal());
            existing.setEstado(value.getEstado());
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