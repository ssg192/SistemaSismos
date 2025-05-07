package mx.com.escom.sismos.external.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.escom.sismos.external.jpa.model.PlacasJpa;

public interface PlacasJpaRepository extends JpaRepository<PlacasJpa, Integer> {
    
}
