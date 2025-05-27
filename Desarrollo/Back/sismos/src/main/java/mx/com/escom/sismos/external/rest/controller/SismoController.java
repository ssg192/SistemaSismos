package mx.com.escom.sismos.external.rest.controller;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.escom.paginacion.Paginacion;
import mx.com.escom.paginacion.PaginacionDTO;
import mx.com.escom.sismos.core.business.input.SismoService;
import mx.com.escom.sismos.external.rest.dto.BusquedaSismoDto;
import mx.com.escom.sismos.external.rest.dto.PlacasDto;
import mx.com.escom.sismos.external.rest.dto.SismoDto;
import mx.com.escom.sismos.external.rest.dto.SismoWithVolcanAndPlacaDto;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.math.BigDecimal;
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
    public Response obtenerSismos(@BeanParam PaginacionDTO paginacion) {
        return Response.ok(sismoService.listaSismos(paginacion.toEntity()).stream().map(SismoDto::fromEntity).toList())
                .build();
    }

    @POST
    @Path("/{fecha}/{magnitud}")
    public Response busquedaSismo(@PathParam("fecha") LocalDate fecha, @PathParam("magnitud") BigDecimal magnitud) {
        var busqueda = sismoService.busquedaSismo(fecha, magnitud).stream().map(BusquedaSismoDto::fromEntity).toList();
        return Response.ok(busqueda).build();

    }

    @GET
    //OBTENER SISMOS CON PLACAS Y VOLCANES
    @Path("/{idSismos}")
    public Response listSismosWithPlacaAndVolcan(@PathParam("idSismos") Integer idSismos) {
        return Response.ok(sismoService.listSismosWithPlacaAndVolcan(idSismos).stream().map(SismoWithVolcanAndPlacaDto::fromEntity).toList())
                .build();
    }

}
