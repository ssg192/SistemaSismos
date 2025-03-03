package mx.com.tarea3.Tarea.external.jpa.repository;

import mx.com.tarea3.Tarea.external.jpa.model.UsuarioJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpa, Integer> {
    Optional<UsuarioJpa> findByEmail(String email);
}
