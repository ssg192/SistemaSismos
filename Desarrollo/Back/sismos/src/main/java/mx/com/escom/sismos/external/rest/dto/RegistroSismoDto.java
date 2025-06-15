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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StringConstants.LOCAL_DATE_FORMAT)
    @JsonProperty
    private LocalDate fecha;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StringConstants.LOCAL_TIME_FORMAT)
    @Schema(implementation = String.class)
    private LocalTime hora;
    @JsonProperty
    @Positive(message = "DATOS NEGATIVOS")
    private BigDecimal magnitud;
    @JsonProperty
    private BigDecimal latitud;
    @JsonProperty
    private BigDecimal longitud;
    @JsonProperty
    @Positive(message = "DATOS NEGATIVOS")
    private BigDecimal profundidad;
    @JsonProperty
    private String referenciaLocalizacion;
    @JsonProperty
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
