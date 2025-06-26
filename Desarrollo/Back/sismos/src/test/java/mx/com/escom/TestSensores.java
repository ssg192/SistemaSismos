package mx.com.escom;


import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.escom.sismos.external.rest.controller.SensoresController;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(SensoresController.class)
class TestSensores {
    @Test
    void ObtenerSensores(){
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .get()
                .then()
                .statusCode(Response.Status.OK.getStatusCode());

    }
}