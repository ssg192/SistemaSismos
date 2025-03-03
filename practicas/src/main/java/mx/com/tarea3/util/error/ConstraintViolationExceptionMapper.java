package mx.com.tarea3.util.error;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    /**
     * Interceptor para el mapeo de errores de formato en los dto
     *
     * @param e Validación que no se cumplio
     * @return Devuelve una respuesta con codigo 400 y la lista de campos no validos
     */
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response toResponse(ConstraintViolationException e) {
        var response = new ErrorResponseDto();
        List<ErrorDetailDto> errores = e.getConstraintViolations().stream()
                .map(ErrorMapper::constraintToError).collect(Collectors.toList());
        response.setDetails(errores);
        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }
}
