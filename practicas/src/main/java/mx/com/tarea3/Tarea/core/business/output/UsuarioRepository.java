package mx.com.tarea3.Tarea.core.business.output;

import mx.com.tarea3.Tarea.core.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario save (Usuario usuario);
    List<Usuario> ListarUsuarios();
    void eliminar (Integer id);
    Optional<Usuario> buscarUsuario(Integer id);
    Optional<Usuario> buscarUsuarioPorCorreo(String email);
    Optional<Usuario> getUsuarioById(Integer id);
}
