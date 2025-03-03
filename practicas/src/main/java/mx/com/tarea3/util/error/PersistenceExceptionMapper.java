package mx.com.tarea3.util.error;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Slf4j
@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {
    @Override
    public Response toResponse(PersistenceException persistenceException) {
        log.error("PersistenceExceptionMapper", persistenceException);
        var response = new ErrorResponseDto();
        ErrorDetailDto error =  ErrorDetailDto.builder()
                .code(ErrorCodesEnum.CAPA_PERSISTENCIA.name())
                .type(ErrorType.REQUEST)
                .message(ErrorCodesEnum.CAPA_PERSISTENCIA.getDetail())
                .build();
        response.setDetails(List.of(error));
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
    }
}
