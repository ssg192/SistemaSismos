package mx.com.escom.sismos.external.jpa.repository;

import mx.com.escom.sismos.external.jpa.model.SismoJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SismoJpaRepository extends JpaRepository<SismoJpa, Integer> {
}
