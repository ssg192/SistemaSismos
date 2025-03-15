package mx.com.escom.sismos.external.jpa.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mx.com.escom.sismos.core.business.output.SismoRepository;
import mx.com.escom.sismos.core.entity.Sismo;
import mx.com.escom.sismos.external.jpa.model.SismoJpa;
import mx.com.escom.sismos.external.jpa.repository.SismoJpaRepository;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class SismoDao implements SismoRepository {
    @Inject
    SismoJpaRepository sismoJpaRepository;

    @Override
    public List<Sismo> obtenerSismos() {
        return sismoJpaRepository.findAll().stream().map(SismoJpa::toEntity).collect(Collectors.toList());
    }
}
