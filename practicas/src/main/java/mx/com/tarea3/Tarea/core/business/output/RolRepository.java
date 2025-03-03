package mx.com.tarea3.Tarea.core.business.output;

import mx.com.tarea3.Tarea.core.entity.Rol;

import java.util.Optional;

public interface RolRepository {
    Optional<Rol> findById(Integer id);
}
