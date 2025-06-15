package mx.com.escom.sismos.external.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import mx.com.escom.util.StringConstants;
import mx.com.escom.util.annotations.ParamLocalDateFormat;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.reactive.RestQuery;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class FiltrosFechaDTO {
    @RestQuery
    @NotNull
    @ParamLocalDateFormat(value = StringConstants.LOCAL_DATE_FORMAT)
    @Schema(description = "Fecha de inicio del periodo", implementation = String.class, examples = "01/02/2025", required = true)
    private LocalDate inicio;

    @RestQuery
    @NotNull
    @ParamLocalDateFormat(value = StringConstants.LOCAL_DATE_FORMAT)
    @Schema(description = "Fecha de fin del periodo", implementation = String.class, examples = "01/02/2025", required = true)
    private LocalDate fin;
}
