package mx.com.escom;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.escom.sismos.external.rest.controller.SismoController;
import mx.com.escom.sismos.external.rest.dto.RegistroSismoDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static io.restassured.RestAssured.given;


@QuarkusTest
@TestHTTPEndpoint(SismoController.class)
class TestSismos {
    private static final String PATH_FIND_VOLCAN_SISMO_BY_ID = "/{idSismos}";
    private static final String PATH_FIND_SISMO_BY_PERIOD = "Busqueda-by-periodo";
    private static final String PATH_REGISTER_SISMO = "/registroSismo";
    private static final String PATH_DOWNLOAD_SISMOS = "descargar-csv";

    @Test
    void volcanesSismosPlacas(){
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .pathParam("idSismos", 1)
                .when()
                .get(PATH_FIND_VOLCAN_SISMO_BY_ID)
                .then()
                .statusCode(Response.Status.OK.getStatusCode());



    }
    @Test
    void SismosPeriodos(){
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .queryParam("inicio","01/01/2025")
                .queryParam("fin", "01/01/2025")
                .when()
                .get(PATH_FIND_SISMO_BY_PERIOD)
                .then().log().all()
                .statusCode(Response.Status.OK.getStatusCode());

    }

    @Test
    void Paginacion(){
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .queryParam("numeroPagina", "1")
                .queryParam("cantidadFilas", "5")
                .when()
                .get()
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void RegistroSismo (){
        var body = RegistroSismoDto.builder()
                .hora(LocalTime.now())
                .fecha(LocalDate.now())
                .estatus("Confirmado")
                .latitud(BigDecimal.valueOf(58.54))
                .profundidad(BigDecimal.valueOf(100))
                .longitud(BigDecimal.valueOf(80.6))
                .magnitud(BigDecimal.valueOf(8.0))
                .build();

        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .body(body)
                .post(PATH_REGISTER_SISMO)
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void descargaCSV(){
        given()
                .header(HttpHeaders.ACCEPT, "text/csv")
                .when()
                .get(PATH_DOWNLOAD_SISMOS)
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

}


