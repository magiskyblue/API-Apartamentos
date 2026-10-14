package com.api.apartamentos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.apartamentos.models.ClienteModel;
import com.api.apartamentos.models.PropiedadModel;
import com.api.apartamentos.repositories.IClienteRepository;
import com.api.apartamentos.repositories.IPropiedadRepository;

@Service
public class PropiedadService extends BaseCrudService<PropiedadModel> {

    private final IPropiedadRepository propiedadRepository;
    private final IClienteRepository clienteRepository;

    public PropiedadService(IPropiedadRepository propiedadRepository, IClienteRepository clienteRepository) {
        super(propiedadRepository);
        this.propiedadRepository = propiedadRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public PropiedadModel save(PropiedadModel propiedad) {
        if (propiedad.getPropietario() == null || propiedad.getPropietario().getId() == null) {
            throw new IllegalArgumentException("El propietario es obligatorio");
        }
        ClienteModel propietario = clienteRepository.findById(propiedad.getPropietario().getId())
                .orElseThrow(() -> new IllegalArgumentException("El propietario no existe"));
        if (propietario.getTipo() != ClienteModel.TipoCliente.PROPIETARIO) {
            throw new IllegalArgumentException("El cliente seleccionado no es de tipo PROPIETARIO");
        }
        if (propiedad.getIdPropiedad() == null && propietario.getEstatus() != ClienteModel.ClienteStatus.ACTIVO) {
            throw new IllegalArgumentException("El propietario no está activo");
        }
        propiedad.setPropietario(propietario);
        return super.save(propiedad);
    }

    public List<PropiedadModel> getAllPropiedades() {
        return findAll();
    }

    public Optional<PropiedadModel> getPropiedadById(Long id) {
        return findById(id);
    }

    public PropiedadModel savePropiedad(PropiedadModel propiedad) {
        return save(propiedad);
    }

    public void deletePropiedad(Long id) {
        deleteById(id);
    }

    public List<PropiedadModel> getPropiedadesByTipo(String tipo) {
        return propiedadRepository.findByTipoIgnoreCase(tipo);
    }

    public List<PropiedadModel> getPropiedadesByPropietario(Long idPropietario) {
        return propiedadRepository.findByPropietario_Id(idPropietario);
    }
}