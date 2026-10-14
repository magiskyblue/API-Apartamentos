package com.api.apartamentos.services;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.apartamentos.models.ClienteModel;
import com.api.apartamentos.models.PropiedadModel;
import com.api.apartamentos.models.ReservaModel;
import com.api.apartamentos.models.UnidadRentableModel;
import com.api.apartamentos.repositories.IClienteRepository;
import com.api.apartamentos.repositories.IReservaRepository;
import com.api.apartamentos.repositories.IUnidadRentableRepository;

@Service
public class ReservaService extends BaseCrudService<ReservaModel> {
    private final IReservaRepository reservaRepository;
    private final IUnidadRentableRepository unidadRepository;
    private final IClienteRepository clienteRepository;

    public ReservaService(IReservaRepository repository, IUnidadRentableRepository unidadRepository,
            IClienteRepository clienteRepository) {
        super(repository);
        this.reservaRepository = repository;
        this.unidadRepository = unidadRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public ReservaModel save(ReservaModel reserva) {
        if (reserva.getUnidad() == null || reserva.getUnidad().getIdUnidad() == null) {
            throw new IllegalArgumentException("La unidad es obligatoria");
        }
        if (reserva.getCliente() == null || reserva.getCliente().getId() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (reserva.getFechaEntrada() == null || reserva.getFechaSalida() == null
                || !reserva.getFechaSalida().isAfter(reserva.getFechaEntrada())) {
            throw new IllegalArgumentException("El rango de fechas no es válido");
        }

        // Se bloquea la unidad y se reemplazan los objetos "stub" (solo id) por los reales.
        UnidadRentableModel unidad = unidadRepository.findByIdForUpdate(reserva.getUnidad().getIdUnidad())
                .orElseThrow(() -> new IllegalArgumentException("La unidad no existe"));
        ClienteModel cliente = clienteRepository.findById(reserva.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("El cliente no existe"));
        reserva.setUnidad(unidad);
        reserva.setCliente(cliente);

        boolean activa = !"CANCELADA".equals(reserva.getEstado()) && !"RECHAZADA".equals(reserva.getEstado());
        if (activa) {
            if (reserva.getIdReservacion() == null) {
                PropiedadModel propiedad = unidad.getPropiedad();
                if (propiedad != null && propiedad.getEstado() != PropiedadModel.EstadoPropiedad.DISPONIBLE) {
                    throw new IllegalArgumentException("La propiedad no está disponible para nuevas reservas");
                }
                if (cliente.getEstatus() != ClienteModel.ClienteStatus.ACTIVO) {
                    throw new IllegalArgumentException("El cliente no está activo");
                }
            }
            if (reservaRepository.existeSolapamiento(unidad.getIdUnidad(), reserva.getIdReservacion(),
                    reserva.getFechaEntrada(), reserva.getFechaSalida())) {
                throw new IllegalArgumentException("La unidad ya está reservada en ese rango");
            }
        }

        if (reserva.getImporteTotal() == null && unidad.getPrecioNoche() != null) {
            long noches = Math.max(1, ChronoUnit.DAYS.between(
                    reserva.getFechaEntrada().toLocalDate(), reserva.getFechaSalida().toLocalDate()));
            reserva.setImporteTotal(unidad.getPrecioNoche().multiply(BigDecimal.valueOf(noches)));
        }
        return reservaRepository.save(reserva);
    }
}