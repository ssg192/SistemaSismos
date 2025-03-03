package mx.com.tarea3.Tarea.core.business.input;

import io.vavr.control.Either;
import mx.com.tarea3.Tarea.core.entity.Usuario;
import mx.com.tarea3.util.error.ErrorCodesEnum;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Either<ErrorCodesEnum, Usuario> crearUsuario(Usuario entity);
    Either<ErrorCodesEnum, Usuario> actualizarUsuario(Integer id, Usuario entity);
    Either<ErrorCodesEnum, Boolean> eliminarUsuario(Integer id);
    List<Usuario> listarUsuarios();
    Either<ErrorCodesEnum, String> inicioSesion(Usuario entity) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException;
    Optional<Usuario> getUsuarioById(Integer id);

}
