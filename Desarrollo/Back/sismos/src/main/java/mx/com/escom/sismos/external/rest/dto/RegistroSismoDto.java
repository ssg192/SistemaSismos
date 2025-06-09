package mx.com.escom.sismos.external.rest.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.escom.sismos.core.entity.Sismo;
import mx.com.escom.util.StringConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroSismoDto {
    @NotNull(message = "DATOS INCOMPLETOS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StringConstants.LOCAL_DATE_FORMAT)
    @JsonProperty
    private LocalDate fecha;
    @JsonProperty
    @NotNull(message = "DATOS INCOMPLETOS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StringConstants.LOCAL_TIME_FORMAT)
    @Schema(implementation = String.class)
    private LocalTime hora;
    @JsonProperty
    @NotNull(message = "DATOS INCOMPLETOS")
    @Positive(message = "DATOS NEGATIVOS")
    private BigDecimal magnitud;
    @JsonProperty
    @NotNull(message = "DATOS INCOMPLETOS")
    private BigDecimal latitud;
    @JsonProperty
    @NotNull(message = "DATOS INCOMPLETOS")
    private BigDecimal longitud;
    @JsonProperty
    @NotNull(message = "DATOS INCOMPLETOS")
    @Positive(message = "DATOS NEGATIVOS")
    private BigDecimal profundidad;
    @JsonProperty
    @NotNull(message = "DATOS INCOMPLETOS")
    private String referenciaLocalizacion;
    @JsonProperty
    @NotNull(message = "DATOS INCOMPLETOS")
    private String estatus;

    public Sismo toEntity() {
        return Sismo.builder()
                .fecha(fecha)
                .hora(hora)
                .magnitud(magnitud)
                .latitud(latitud)
                .longitud(longitud)
                .profundidad(profundidad)
                .referenciaLocalizacion(referenciaLocalizacion)
                .estatus(estatus)
                .build();

    }
}
