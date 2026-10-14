package com.api.apartamentos.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "reservaciones")
public class ReservaModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_reservacion") private Long idReservacion;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "id_unidad", nullable = false)
    @JsonIgnoreProperties({"propiedad", "hibernateLazyInitializer", "handler"}) private UnidadRentableModel unidad;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "id_cliente", nullable = false)
    @JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"}) private ClienteModel cliente;
    @NotNull
    @Column(name = "fecha_entrada", nullable = false) private LocalDateTime fechaEntrada;
    @NotNull
    @Column(name = "fecha_salida", nullable = false) private LocalDateTime fechaSalida;
    @Column(name = "fecha_checkin") private LocalDateTime fechaCheckin;
    @Column(name = "fecha_checkout") private LocalDateTime fechaCheckout;
    @DecimalMin("0.01")
    @Column(name = "importe_total", nullable = false, precision = 12, scale = 2) private BigDecimal importeTotal;
    @NotBlank @Size(max = 20)
    @Column(nullable = false, length = 20) private String estado = "PENDIENTE";
    @Size(max = 500)
    @Column(length = 500) private String comentario;

    @JsonIgnore
    @AssertTrue(message = "La fecha de salida debe ser posterior a la fecha de entrada")
    public boolean isRangoFechasValido() { return fechaEntrada == null || fechaSalida == null || fechaSalida.isAfter(fechaEntrada); }

    public Long getIdReservacion() { return idReservacion; } public void setIdReservacion(Long value) { idReservacion = value; }
    public UnidadRentableModel getUnidad() { return unidad; } public void setUnidad(UnidadRentableModel value) { unidad = value; }
    public ClienteModel getCliente() { return cliente; } public void setCliente(ClienteModel value) { cliente = value; }
    public LocalDateTime getFechaEntrada() { return fechaEntrada; } public void setFechaEntrada(LocalDateTime value) { fechaEntrada = value; }
    public LocalDateTime getFechaSalida() { return fechaSalida; } public void setFechaSalida(LocalDateTime value) { fechaSalida = value; }
    public LocalDateTime getFechaCheckin() { return fechaCheckin; } public void setFechaCheckin(LocalDateTime value) { fechaCheckin = value; }
    public LocalDateTime getFechaCheckout() { return fechaCheckout; } public void setFechaCheckout(LocalDateTime value) { fechaCheckout = value; }
    public BigDecimal getImporteTotal() { return importeTotal; } public void setImporteTotal(BigDecimal value) { importeTotal = value; }
    public String getEstado() { return estado; } public void setEstado(String value) { estado = value; }
    public String getComentario() { return comentario; } public void setComentario(String value) { comentario = value; }
}