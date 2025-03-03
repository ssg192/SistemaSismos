package mx.com.tarea3.Tarea.core.business.implementation;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.smallrye.jwt.build.Jwt;
import io.vavr.control.Either;
import mx.com.tarea3.Tarea.core.business.input.UsuarioService;
import mx.com.tarea3.Tarea.core.business.output.RolRepository;
import mx.com.tarea3.Tarea.core.business.output.UsuarioRepository;
import mx.com.tarea3.Tarea.core.entity.Rol;
import mx.com.tarea3.Tarea.core.entity.Usuario;
import mx.com.tarea3.util.error.ErrorCodesEnum;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;


@ApplicationScoped
public class UsuarioBs implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;
    @Inject
    RolRepository rolRepository;

    @Override
    public Either<ErrorCodesEnum, Usuario> crearUsuario(Usuario entity) {

        String emailRegex = "^[a-zA-Z0-9]+@[a-z]+\\.[a-z.]{2,6}$";
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";

        if (entity.getNombre() == null || entity.getEmail() == null || entity.getPassword() == null) {
            return Either.left(ErrorCodesEnum.RNS001);
        }

        if (!entity.getEmail().matches(emailRegex)) {
            return Either.left(ErrorCodesEnum.RNS002);
        }
        if (!entity.getPassword().matches(passwordRegex)) {
            return Either.left(ErrorCodesEnum.RNS003);
        }

        String hashedPassword = BCrypt.withDefaults().hashToString(12, entity.getPassword().toCharArray());
        entity.setPassword(hashedPassword);

        Optional<Rol> rolUsuario = rolRepository.findById(2);
        if (rolUsuario.isPresent()) {
            entity.setRoles(new ArrayList<>());
            entity.getRoles().add(rolUsuario.get());
        } else {
            return Either.left(ErrorCodesEnum.RNS004);
        }

        return Either.right(usuarioRepository.save(entity));
    }

    @Override
   public Either<ErrorCodesEnum, Boolean> eliminarUsuario(Integer id){
        Either<ErrorCodesEnum,Boolean> result = Either.left(ErrorCodesEnum.RNS005);
        var existeUsuario=usuarioRepository.buscarUsuario(id);
        if (existeUsuario.isPresent()) {
            usuarioRepository.eliminar(id);
            result=Either.right(true);
        }
        return result;
    }

    @Override
   public  Either<ErrorCodesEnum, Usuario> actualizarUsuario(Integer id, Usuario entity) {
        Either<ErrorCodesEnum, Usuario> result=Either.left(ErrorCodesEnum.RNS005);
        var existeUsuario= buscarUsuario(id);
        if (existeUsuario.isPresent()) {
            var usuarioActualizar= existeUsuario.get();
            String hashedPassword = BCrypt.withDefaults().hashToString(12, entity.getPassword().toCharArray());
            usuarioActualizar.setPassword(hashedPassword);
            usuarioActualizar.setNombre(entity.getNombre());
            usuarioActualizar.setEmail(entity.getEmail());
            result = Either.right(usuarioRepository.save(usuarioActualizar));
        }
        return result;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.ListarUsuarios();
    }

    @Override
    public Either<ErrorCodesEnum, String> inicioSesion(Usuario entity) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        if (entity.getEmail() == null || entity.getPassword() == null) {
            return Either.left(ErrorCodesEnum.RNS001);
        }
        var usuario = buscarUsuarioPorCorreo(entity.getEmail());
        if (usuario == null) {
            return Either.left(ErrorCodesEnum.RNS005);
        }
        boolean contrasenaValida = BCrypt.verifyer().verify(entity.getPassword().toCharArray(), usuario.getPassword()).verified;
        if (!contrasenaValida) {
            return Either.left(ErrorCodesEnum.RNS007);
        }

        String token = generarTokenJWT(usuario);
        return Either.right(token);
    }

    @Override
    public Optional<Usuario> getUsuarioById(Integer id) {
        return usuarioRepository.getUsuarioById(id);
    }


    public Optional<Usuario> buscarUsuario(Integer id) {
        return usuarioRepository.buscarUsuario(id);
    }

    public Usuario buscarUsuarioPorCorreo(String email) {
        return usuarioRepository.buscarUsuarioPorCorreo(email).orElse(null);
    }

    public String generarTokenJWT(Usuario usuario) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {

        final String PRIVATE_KEY_PATH = "src/main/resources/privateKey.pem";
        String privateKeyContent = new String(Files.readAllBytes(Paths.get(PRIVATE_KEY_PATH)),StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(privateKeyContent);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

        return Jwt.issuer("tu_aplicacion")
                .subject(usuario.getId().toString())
                .groups(Set.copyOf(usuario.getRoles().stream().map(Rol::getNombre).toList()))
                .expiresAt(System.currentTimeMillis() / 1000 + 3600)
                .sign(privateKey);
    }
    }









