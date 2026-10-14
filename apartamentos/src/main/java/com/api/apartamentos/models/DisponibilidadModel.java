package com.api.apartamentos.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "disponibilidades")
public class DisponibilidadModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_disponibilidad") private Long idDisponibilidad;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "id_unidad", nullable = false)
    @JsonIgnoreProperties({"propiedad", "hibernateLazyInitializer", "handler"}) private UnidadRentableModel unidad;
    @NotNull
    @Column(name = "fecha_inicio", nullable = false) private LocalDateTime fechaInicio;
    @NotNull
    @Column(name = "fecha_fin", nullable = false) private LocalDateTime fechaFin;
    @NotNull
    @Column(nullable = false) private Boolean disponible;
    @DecimalMin("0.01")
    @Column(name = "precio_especial", precision = 12, scale = 2) private BigDecimal precioEspecial;

    @JsonIgnore
    @AssertTrue(message = "La fecha fin debe ser posterior a la fecha de inicio")
    public boolean isRangoFechasValido() { return fechaInicio == null || fechaFin == null || fechaFin.isAfter(fechaInicio); }

    public Long getIdDisponibilidad() { return idDisponibilidad; } public void setIdDisponibilidad(Long value) { idDisponibilidad = value; }
    public UnidadRentableModel getUnidad() { return unidad; } public void setUnidad(UnidadRentableModel value) { unidad = value; }
    public LocalDateTime getFechaInicio() { return fechaInicio; } public void setFechaInicio(LocalDateTime value) { fechaInicio = value; }
    public LocalDateTime getFechaFin() { return fechaFin; } public void setFechaFin(LocalDateTime value) { fechaFin = value; }
    public Boolean getDisponible() { return disponible; } public void setDisponible(Boolean value) { disponible = value; }
    public BigDecimal getPrecioEspecial() { return precioEspecial; } public void setPrecioEspecial(BigDecimal value) { precioEspecial = value; }
}