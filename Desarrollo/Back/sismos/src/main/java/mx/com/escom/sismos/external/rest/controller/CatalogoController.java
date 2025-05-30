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
import mx.com.escom.sismos.external.rest.dto.VolcanesDto;
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
    @Path("/placas")
    @APIResponse(responseCode = "200", description = "Petición exitosa", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = PlacasDto.class)))
    @Operation(operationId = "listCatalogoPlacas", summary = "Obtiene el catalogo de placas")
    public Response getCatalogoPlacas() {
        return Response.ok(sismoService.listAllPlacas().stream().map(PlacasDto::fromEntity).toList()).build();
    }

    @GET
    @Path("/volcanes")
    @APIResponse(responseCode = "200", description = "Petición exitosa", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = VolcanesDto.class)))
    @Operation(operationId = "listCatalogoVolcan", summary = "Obtiene el catalogo de volcanes")
    public Response getCatalogoVolcan(){
        return Response.ok(sismoService.listAllVolcanes().stream().map(VolcanesDto::fromEntity).toList()).build();
    }

}
