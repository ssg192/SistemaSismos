package mx.com.tarea3.Tarea.external.rest.controller;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import mx.com.tarea3.Tarea.core.business.input.UsuarioService;
import mx.com.tarea3.Tarea.core.entity.Usuario;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/inicio")
@Tag(name = "Renderización de html's")
public class RenderizacionHtmlController {

    @Inject
    Template inicio;
    @Inject
    Template usuario;
    @Inject
    Template admin;
    @Inject
    Template lista;
    @Inject
    Template registrar;

    @Inject
    UsuarioService usuarioService;

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getinicio() {
        return inicio.instance();
    }

    @GET
    @Path("/usuario")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getUsuario() {
        return usuario.instance();
    }

    @GET
    @Path("/admin")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getAdmin() {
        return admin.instance();
    }

    @GET
    @Path("/admin/lista-usuarios")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getListaUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
             return lista.data("usuarios", usuarios);

    }
  @GET
    @Path("/admin/registrar")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getregistrar() {
        return registrar.instance();
    }

}