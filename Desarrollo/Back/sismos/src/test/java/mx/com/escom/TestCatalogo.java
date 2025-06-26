package mx.com.escom;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.escom.sismos.external.rest.controller.CatalogoController;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(CatalogoController.class)
class TestCatalogo {
    private static final String PATH_OBTAIN_PLACAS = "/placas";
    private static final String PATH_OBTAIN_VOLCANES = "/volcanes";
    @Test
    void ObtenerPlacas(){
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .get(PATH_OBTAIN_PLACAS)
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void ObtenerVolcanes(){
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .get(PATH_OBTAIN_VOLCANES)
                .then()
                .statusCode(Response.Status.OK.getStatusCode());

    }


}



