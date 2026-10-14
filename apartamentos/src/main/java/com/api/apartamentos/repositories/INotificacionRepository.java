package com.api.apartamentos.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api.apartamentos.models.NotificacionModel;

public interface INotificacionRepository extends JpaRepository<NotificacionModel, Long> {
    @Override
    @EntityGraph(attributePaths = {"remitente", "destinatario"})
    List<NotificacionModel> findAll();

    @Override
    @EntityGraph(attributePaths = {"remitente", "destinatario"})
    Optional<NotificacionModel> findById(Long id);

    @EntityGraph(attributePaths = {"remitente", "destinatario"})
    List<NotificacionModel> findByDestinatario_Id(Long idCliente);
}