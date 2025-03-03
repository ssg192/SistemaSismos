package mx.com.tarea3.Tarea.external.rest.controller;


import mx.com.tarea3.Tarea.core.business.input.UsuarioService;
import mx.com.tarea3.Tarea.external.rest.dto.UsuarioDto;
import mx.com.tarea3.util.error.ErrorMapper;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/inicio")
@RolesAllowed("ROLE_ADMIN")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Operaciones del administrador")
public class AdministradorController {

    @Inject
    UsuarioService usuarioService;
    @GET
    @Path("/admin/usuarios")
    public Response listarUsuarios() {
        var usuarios= usuarioService.listarUsuarios().stream().map(UsuarioDto::fromEntity).collect(Collectors.toList());
        return Response.ok(usuarios).build();
    }
    @Path("/editar/{id}")
    @PUT
    public Response editar(@PathParam("id") Integer id, UsuarioDto usuarioDto) {
        return usuarioService.actualizarUsuario(id,usuarioDto.toEntity())
                .map(UsuarioDto::fromEntity).map(Response::ok).getOrElseGet(ErrorMapper::errorCodeToResponseBuilder).build();
    }

    @Path("/eliminar/{id}")
    @DELETE
    public Response eliminar(@PathParam("id") Integer id) {
        return usuarioService.eliminarUsuario(id).map(Response::ok).getOrElseGet(ErrorMapper::errorCodeToResponseBuilder).build();
    }

    @POST
    @Path("/registrarUsuario")
    public Response registrarUsuario(@Valid UsuarioDto usuarioDto) {
        return usuarioService.crearUsuario(usuarioDto.toEntity()).map(UsuarioDto::fromEntity).map(Response::ok)
                .getOrElseGet(ErrorMapper::errorCodeToResponseBuilder).build();

    }

}
