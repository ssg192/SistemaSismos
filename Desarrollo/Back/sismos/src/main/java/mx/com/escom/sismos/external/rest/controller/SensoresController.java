package mx.com.escom.sismos.external.rest.controller;

import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import mx.com.escom.sismos.core.business.input.SensoresService;
import mx.com.escom.sismos.external.rest.dto.SensoresDto;

@Path("/inicio/sensores")
@Tag(name = "Visualizacion de sensores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensoresController {
    @Inject
    SensoresService sensoresService;
    @GET
    public Response obtenerSensores() {
        var sensores = sensoresService.getAllSensores().stream().map(SensoresDto::fromEntity).collect(Collectors.toList());
        return Response.ok(sensores).build();
    }
}
