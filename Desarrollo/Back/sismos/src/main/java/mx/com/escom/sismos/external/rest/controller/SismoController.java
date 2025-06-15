package mx.com.escom.sismos.external.rest.controller;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.escom.paginacion.PaginacionDTO;
import mx.com.escom.sismos.core.business.input.SismoService;
import mx.com.escom.sismos.external.rest.dto.*;
import mx.com.escom.util.error.ErrorMapper;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;


@Path("/inicio")
@Tag(name = "Visualizacion de sismos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SismoController {

    private final SismoService sismoService;

    @Inject
    public SismoController(SismoService sismoService) {
        this.sismoService = sismoService;
    }

    @GET
    @APIResponse(responseCode = "200", description = "Petición exitosa", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = SismoDto.class)))
    public Response obtenerSismos(@BeanParam PaginacionDTO paginacion) {
        return Response.ok(sismoService.listaSismos(paginacion.toEntity()).stream().map(SismoDto::fromEntity).toList())
                .build();
    }

    @GET
    @Path("Busqueda-by-periodo")
    @APIResponse(responseCode = "200", description = "Petición exitosa", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = BusquedaSismoDto.class)))
    public Response busquedaSismo(@BeanParam FiltrosFechaDTO filtrosFecha) {
        var busqueda = sismoService.busquedaSismo(filtrosFecha.getInicio(),filtrosFecha.getFin()).stream().map(BusquedaSismoDto::fromEntity).toList();
        return Response.ok(busqueda).build();

    }

    @GET
    @Path("/{idSismos}")
    @APIResponse(responseCode = "200", description = "Petición exitosa", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = SismoWithVolcanAndPlacaDto.class)))
    public Response listSismosWithPlacaAndVolcan(@PathParam("idSismos") Integer idSismos) {
        return Response.ok(sismoService.listSismosWithPlacaAndVolcan(idSismos).stream().map(SismoWithVolcanAndPlacaDto::fromEntity).toList())
                .build();
    }

    @POST
    @Path("/registroSismo")
    @APIResponse(responseCode = "200", description = "Petición exitosa", content = @Content(schema = @Schema(implementation = Boolean.class)))
    public Response registroSismo(@Valid RegistroSismoDto registroSismoDto) {
        return sismoService.create(registroSismoDto.toEntity()).map(Response::ok)
                .getOrElseGet(ErrorMapper::errorCodeToResponseBuilder).build();
    }

    @GET
    @Path("descargar-csv")
    @Produces("text/csv")
    public Response descargarCsv() {
        String csv = sismoService.listAllRegistros();
        return Response.ok(csv.getBytes(StandardCharsets.UTF_8))
                .header("Content-Disposition", "attachment; filename=\"sismos.csv\"")
                .build();
    }

}
