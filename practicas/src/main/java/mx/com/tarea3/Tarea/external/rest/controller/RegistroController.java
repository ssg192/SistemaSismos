package mx.com.tarea3.Tarea.external.rest.controller;

import mx.com.tarea3.Tarea.core.business.input.UsuarioService;
import mx.com.tarea3.Tarea.core.entity.Usuario;
import mx.com.tarea3.Tarea.external.rest.dto.InicioSesionDto;
import mx.com.tarea3.Tarea.external.rest.dto.UsuarioDto;
import mx.com.tarea3.util.error.ErrorMapper;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@Path("/inicio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Registro,inicio de sesion y vista de los de los usuarios")
public class RegistroController {
    @Inject
    UsuarioService usuarioService;

    @POST
    @Path("/registrarse")
    public Response registrarUsuario(@Valid UsuarioDto usuarioDto) {
        return usuarioService.crearUsuario(usuarioDto.toEntity()).map(UsuarioDto::fromEntity).map(Response::ok)
                .getOrElseGet(ErrorMapper::errorCodeToResponseBuilder).build();

    }

    @POST
    @Path("/iniciarsesion")
    public Response iniciarSesion(@Valid InicioSesionDto inicioSesionDto) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        var resultado = usuarioService.inicioSesion(inicioSesionDto.toEntity());

        return resultado.fold(
                error -> Response.status(Response.Status.UNAUTHORIZED)
                        .entity(error.getName())
                        .build(),
                token -> Response.ok(token).build()
        );
    }

    @GET
    @Path("/usuario/perfil")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public Response getUsuarioPerfil(@Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();
        Optional<Usuario> usuarioOpt = usuarioService.getUsuarioById(Integer.parseInt(userId));
        if (usuarioOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(usuarioOpt.get()).build();
    }






}
