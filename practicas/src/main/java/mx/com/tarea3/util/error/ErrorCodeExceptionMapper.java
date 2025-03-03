package mx.com.tarea3.util.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ErrorCodeExceptionMapper implements ExceptionMapper<ErrorCodeException> {

    @Override
    public Response toResponse(ErrorCodeException errorCodeException) {
        return ErrorMapper.errorCodeToResponseBuilder(errorCodeException.getErrorCode()).build();
    }

}
