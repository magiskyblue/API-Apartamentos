package com.api.apartamentos.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "propiedades")
public class PropiedadModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propiedad")
    private Long idPropiedad;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_propietario", nullable = false)
    @JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
    private ClienteModel propietario;

    @NotBlank @Size(max = 40)
    @Column(name = "tipo_propiedad", nullable = false, length = 40)
    private String tipo;

    @NotBlank @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String titulo;

    @NotBlank @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String descripcion;

    @NotBlank @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String direccion;

    @NotBlank @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String ciudad;

    @NotBlank @Size(max = 50)
    @Column(name = "estado", nullable = false, length = 50)
    private String estadoFederativo;

    @NotBlank @Size(max = 10)
    @Column(name = "codigo_postal", nullable = false, length = 10)
    private String codigoPostal;

    @NotBlank @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String pais;

    @NotNull @DecimalMin("-90.0") @DecimalMax("90.0")
    @Column(nullable = false)
    private Double latitud = 0.0;

    @NotNull @DecimalMin("-180.0") @DecimalMax("180.0")
    @Column(nullable = false)
    private Double longitud = 0.0;

    @NotNull @DecimalMin("0.01")
    @Column(name = "precio_noche", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioNoche;

    @NotNull @Min(1)
    @Column(nullable = false)
    private Integer capacidad;

    @NotNull @Min(0)
    @Column(name = "num_habitaciones", nullable = false)
    private Integer numHabitaciones;

    @NotNull @Min(0)
    @Column(name = "num_banos", nullable = false)
    private Integer numBanos;

    @NotNull @Min(1)
    @Column(name = "metro_cuadrados", nullable = false)
    private Integer metrosCuadrados;

    @Size(max = 500)
    @Column(length = 500)
    private String comodidades;

    @Size(max = 500)
    @Column(length = 500)
    private String reglas;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_hab", nullable = false, length = 20)
    private EstadoPropiedad estado = EstadoPropiedad.DISPONIBLE;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @PrePersist
    void onCreate() {
        fechaRegistro = LocalDateTime.now();
        fechaActualizacion = fechaRegistro;
        if (latitud == null) latitud = 0.0;
        if (longitud == null) longitud = 0.0;
    }

    @PreUpdate
    void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
        if (latitud == null) latitud = 0.0;
        if (longitud == null) longitud = 0.0;
    }

    public enum EstadoPropiedad { DISPONIBLE, NO_DISPONIBLE, MANTENIMIENTO }

    public Long getIdPropiedad() { return idPropiedad; }
    public void setIdPropiedad(Long value) { idPropiedad = value; }
    public ClienteModel getPropietario() { return propietario; }
    public void setPropietario(ClienteModel value) { propietario = value; }
    public String getTipo() { return tipo; }
    public void setTipo(String value) { tipo = value; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String value) { titulo = value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { descripcion = value; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String value) { direccion = value; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String value) { ciudad = value; }
    public String getEstadoFederativo() { return estadoFederativo; }
    public void setEstadoFederativo(String value) { estadoFederativo = value; }
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String value) { codigoPostal = value; }
    public String getPais() { return pais; }
    public void setPais(String value) { pais = value; }
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double value) { latitud = (value != null) ? value : 0.0; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double value) { longitud = (value != null) ? value : 0.0; }
    public BigDecimal getPrecioNoche() { return precioNoche; }
    public void setPrecioNoche(BigDecimal value) { precioNoche = value; }
    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer value) { capacidad = value; }
    public Integer getNumHabitaciones() { return numHabitaciones; }
    public void setNumHabitaciones(Integer value) { numHabitaciones = value; }
    public Integer getNumBanos() { return numBanos; }
    public void setNumBanos(Integer value) { numBanos = value; }
    public Integer getMetrosCuadrados() { return metrosCuadrados; }
    public void setMetrosCuadrados(Integer value) { metrosCuadrados = value; }
    public String getComodidades() { return comodidades; }
    public void setComodidades(String value) { comodidades = value; }
    public String getReglas() { return reglas; }
    public void setReglas(String value) { reglas = value; }
    public EstadoPropiedad getEstado() { return estado; }
    public void setEstado(EstadoPropiedad value) { estado = value; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
}