package com.api.apartamentos.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api.apartamentos.models.ResenaModel;

public interface IResenaRepository extends JpaRepository<ResenaModel, Long> {
    @Override
    @EntityGraph(attributePaths = {"reserva", "cliente"})
    List<ResenaModel> findAll();

    @Override
    @EntityGraph(attributePaths = {"reserva", "cliente"})
    Optional<ResenaModel> findById(Long id);

    @EntityGraph(attributePaths = {"reserva", "cliente"})
    List<ResenaModel> findByReserva_IdReservacion(Long idReservacion);
}