package com.api.apartamentos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.apartamentos.models.PropiedadModel;
import com.api.apartamentos.services.PropiedadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {

    private final PropiedadService propiedadService;

    public PropiedadController(PropiedadService propiedadService) {
        this.propiedadService = propiedadService;
    }

    @GetMapping
    public List<PropiedadModel> getAllPropiedades() {
        return propiedadService.getAllPropiedades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropiedadModel> getPropiedadesById(@PathVariable Long id) {
        Optional<PropiedadModel> propiedad = propiedadService.getPropiedadById(id);
        return propiedad.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ACTIVIDAD: propiedades por tipo
    @GetMapping("/tipo/{tipo}")
    public List<PropiedadModel> getPropiedadesByTipo(@PathVariable String tipo) {
        return propiedadService.getAllPropiedades().stream()
                .filter(p -> p.getTipo() != null && p.getTipo().equalsIgnoreCase(tipo))
                .toList();
    }

    // ACTIVIDAD: propiedades de un propietario
    @GetMapping("/propietario/{idPropietario}")
    public List<PropiedadModel> getPropiedadesByPropietario(@PathVariable Long idPropietario) {
        return propiedadService.getAllPropiedades().stream()
                .filter(p -> p.getPropietario() != null && idPropietario.equals(p.getPropietario().getId()))
                .toList();
    }

    @PostMapping
    public ResponseEntity<PropiedadModel> createPropiedad(@Valid @RequestBody PropiedadModel propiedad) {
        propiedad.setIdPropiedad(null);
        return ResponseEntity.status(201).body(propiedadService.savePropiedad(propiedad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropiedadModel> updatePropiedad(@PathVariable Long id, @Valid @RequestBody PropiedadModel propiedadDetails) {
        Optional<PropiedadModel> propiedadOptional = propiedadService.getPropiedadById(id);
        if (propiedadOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        PropiedadModel propiedadToUpdate = propiedadOptional.get();
        propiedadToUpdate.setDireccion(propiedadDetails.getDireccion());
        propiedadToUpdate.setTipo(propiedadDetails.getTipo());
        propiedadToUpdate.setTitulo(propiedadDetails.getTitulo());
        propiedadToUpdate.setDescripcion(propiedadDetails.getDescripcion());
        propiedadToUpdate.setCiudad(propiedadDetails.getCiudad());
        propiedadToUpdate.setEstadoFederativo(propiedadDetails.getEstadoFederativo());
        propiedadToUpdate.setCodigoPostal(propiedadDetails.getCodigoPostal());
        propiedadToUpdate.setPais(propiedadDetails.getPais());
        propiedadToUpdate.setLatitud(propiedadDetails.getLatitud());
        propiedadToUpdate.setLongitud(propiedadDetails.getLongitud());
        propiedadToUpdate.setPrecioNoche(propiedadDetails.getPrecioNoche());
        propiedadToUpdate.setCapacidad(propiedadDetails.getCapacidad());
        propiedadToUpdate.setNumHabitaciones(propiedadDetails.getNumHabitaciones());
        propiedadToUpdate.setNumBanos(propiedadDetails.getNumBanos());
        propiedadToUpdate.setMetrosCuadrados(propiedadDetails.getMetrosCuadrados());
        propiedadToUpdate.setComodidades(propiedadDetails.getComodidades());
        propiedadToUpdate.setReglas(propiedadDetails.getReglas());
        propiedadToUpdate.setEstado(propiedadDetails.getEstado());
        propiedadToUpdate.setPropietario(propiedadDetails.getPropietario());
        return ResponseEntity.ok(propiedadService.savePropiedad(propiedadToUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropiedad(@PathVariable Long id) {
        if (propiedadService.getPropiedadById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        propiedadService.deletePropiedad(id);
        return ResponseEntity.noContent().build();
    }
}