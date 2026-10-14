package com.api.apartamentos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.apartamentos.models.UnidadRentableModel;

import jakarta.persistence.LockModeType;

public interface IUnidadRentableRepository extends JpaRepository<UnidadRentableModel, Long> {
    @Override
    @EntityGraph(attributePaths = "propiedad")
    List<UnidadRentableModel> findAll();

    @Override
    @EntityGraph(attributePaths = "propiedad")
    Optional<UnidadRentableModel> findById(Long id);

    @EntityGraph(attributePaths = "propiedad")
    List<UnidadRentableModel> findByPropiedad_IdPropiedad(Long idPropiedad);

    // Bloquea la fila de la unidad mientras dura la transacción (evita reservas simultáneas).
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from UnidadRentableModel u where u.idUnidad = :id")
    Optional<UnidadRentableModel> findByIdForUpdate(@Param("id") Long id);
}