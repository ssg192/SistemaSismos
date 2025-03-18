package mx.com.escom.sismos.external.rest.controller;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.escom.sismos.core.business.input.SismoService;
import mx.com.escom.sismos.external.rest.dto.BusquedaSismoDto;
import mx.com.escom.sismos.external.rest.dto.SismoDto;
import mx.com.escom.util.error.ErrorMapper;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;



@Path("/inicio")
@Tag(name = "Visualizacion de sismos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SismoController {

    @Inject
    SismoService sismoService;

    @GET
    public Response obtenerSismos() {
        var sismos= sismoService.listaSismos().stream().map(SismoDto::fromEntity).collect(Collectors.toList());
        return Response.ok(sismos).build();
    }

}
