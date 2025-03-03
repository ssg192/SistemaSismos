package mx.com.tarea3.Tarea.external.jpa.dao;

import mx.com.tarea3.Tarea.core.business.output.RolRepository;
import mx.com.tarea3.Tarea.core.entity.Rol;
import mx.com.tarea3.Tarea.external.jpa.model.RolJpa;
import mx.com.tarea3.Tarea.external.jpa.repository.RolJpaRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class RolDao implements RolRepository {

    @Inject
    RolJpaRepository rolJpaRepository;

    @Override
    public Optional<Rol>findById(Integer id){
        return rolJpaRepository.findById(id).map(RolJpa::toEntity);

    }




}