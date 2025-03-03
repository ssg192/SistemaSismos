package mx.com.tarea3.Tarea.external.jpa.dao;

import mx.com.tarea3.Tarea.core.business.output.UsuarioRepository;
import mx.com.tarea3.Tarea.core.entity.Rol;
import mx.com.tarea3.Tarea.core.entity.Usuario;
import mx.com.tarea3.Tarea.external.jpa.model.UsuarioJpa;
import mx.com.tarea3.Tarea.external.jpa.repository.UsuarioJpaRepository;
import mx.com.tarea3.Tarea.external.rest.dto.UsuarioDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class UsuarioDao implements UsuarioRepository {

    @Inject
    UsuarioJpaRepository usuarioJpaRepository;

    @Inject
    EntityManager entityManager;

    private static final String QUERY_FIND_USUARIO_ID="select usuarios.nombre, usuarios.email, usuarios.password  from usuarios where usuarios.id = :id";
            private static String PARAM= "id";

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioJpaRepository.saveAndFlush(UsuarioJpa.fromEntity(usuario)).toEntity();
    }

    @Override
    public List<Usuario> ListarUsuarios() {
        return usuarioJpaRepository.findAll().stream().map(UsuarioJpa::toEntity).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        usuarioJpaRepository.deleteById(id);
    }


    @Override
    public Optional<Usuario> buscarUsuario(Integer id) {
        return usuarioJpaRepository.findById(id).map(UsuarioJpa::toEntity);
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorCorreo(String email) {
        return usuarioJpaRepository.findByEmail(email).map(UsuarioJpa::toEntity);

    }


    @Override
    public Optional<Usuario> getUsuarioById(Integer id) {
        Object[] result = (Object[]) entityManager.createNativeQuery(QUERY_FIND_USUARIO_ID)
                .setParameter(PARAM, id).getSingleResult();

        if (result == null) {
            return Optional.empty();
        }

        Usuario usuario = Usuario.builder()
                .nombre((String) result[0])
                .email((String) result[1])
                .password((String) result[2])
                .build();

        return Optional.of(usuario);
    }


}

