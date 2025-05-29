package mx.com.escom.sismos.external.rest.controller;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.escom.sismos.core.business.input.SensoresService;
import mx.com.escom.sismos.external.rest.dto.SensoresDto;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/inicio/sensores")
@Tag(name = "Visualizacion de sensores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensoresController {
    private final SensoresService sensoresService;
    @Inject
    public SensoresController(SensoresService sensoresService) {
        this.sensoresService = sensoresService;
    }
    @GET
    @APIResponse(responseCode = "200", description = "Petición exitosa", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = SensoresDto.class)))
    public Response obtenerSensores() {
        var sensores = sensoresService.getAllSensores().stream().map(SensoresDto::fromEntity).toList();
        return Response.ok(sensores).build();
    }
}
