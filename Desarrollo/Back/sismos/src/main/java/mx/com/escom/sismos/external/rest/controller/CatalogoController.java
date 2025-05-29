package mx.com.escom.sismos.external.rest.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.escom.sismos.core.business.input.SismoService;
import mx.com.escom.sismos.external.rest.dto.PlacasDto;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/catalogos")
@Tag(name = "Visualizacion de catalagos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CatalogoController {

    private final SismoService sismoService;

    @Inject
    public CatalogoController(SismoService sismoService) {
        this.sismoService = sismoService;
    }

    @GET
    @APIResponse(responseCode = "200", description = "Petición exitosa", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = PlacasDto.class)))
    @Operation(operationId = "listCatalogoPlacas", summary = "Obtiene el catalogo de placas")
    public Response getCatalogoPlacas() {
        return Response.ok(sismoService.listAllPlacas().stream().map(PlacasDto::fromEntity).toList()).build();
    }

}
