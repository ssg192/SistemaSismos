package mx.com.escom.sismos.external.rest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.escom.sismos.core.entity.Sismo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroSismoDto {
    @JsonProperty
    private LocalDate fecha;
    @JsonProperty
    private LocalTime hora;
    @JsonProperty
    private BigDecimal magnitud;
    @JsonProperty
    private BigDecimal latitud;
    @JsonProperty
    private BigDecimal longitud;
    @JsonProperty
    private BigDecimal profundidad;
    @JsonProperty
    private String referencia_localizacion;
    @JsonProperty
    private String estatus;

    public static RegistroSismoDto fromEntity(Sismo sismo) {
        return RegistroSismoDto.builder()
                .fecha(sismo.getFecha())
                .hora(sismo.getHora())
                .magnitud(sismo.getMagnitud())
                .latitud(sismo.getLatitud())
                .longitud(sismo.getLongitud())
                .profundidad(sismo.getProfundidad())
                .referencia_localizacion(sismo.getReferenciaLocalizacion())
                .estatus(sismo.getEstatus())
                .build();
    }
}
