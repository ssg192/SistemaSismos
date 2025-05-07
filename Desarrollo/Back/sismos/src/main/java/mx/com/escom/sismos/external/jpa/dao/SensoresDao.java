package mx.com.escom.sismos.external.jpa.dao;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mx.com.escom.sismos.core.business.output.SensoresRepository;
import mx.com.escom.sismos.core.entity.Sensores;
import mx.com.escom.sismos.external.jpa.model.SensoresJpa;
import mx.com.escom.sismos.external.jpa.repository.SensoresJpaRepository;

@ApplicationScoped
public class SensoresDao implements SensoresRepository{
    @Inject
    SensoresJpaRepository sensoresJpaRepository;
    @Override
    public List<Sensores> getAllSensores() {
        return sensoresJpaRepository.findAll().stream().map(SensoresJpa::toEntity).collect(Collectors.toList());

    }
}