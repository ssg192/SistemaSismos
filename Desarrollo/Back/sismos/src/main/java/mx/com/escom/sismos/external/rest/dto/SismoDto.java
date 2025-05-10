package mx.com.escom.sismos.external.rest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mx.com.escom.sismos.core.entity.Sismo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SismoDto {
    @JsonProperty
    private Integer id;
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
    private String referenciaLocalizacion;
    @JsonProperty
    private String estatus;
    @JsonProperty
    private Integer placaId;



    public static SismoDto fromEntity(Sismo sismo) {
        return SismoDto.builder()
                .id(sismo.getId())
                .fecha(sismo.getFecha())
                .hora(sismo.getHora())
                .magnitud(sismo.getMagnitud())
                .latitud(sismo.getLatitud())
                .longitud(sismo.getLongitud())
                .profundidad(sismo.getProfundidad())
                .referenciaLocalizacion(sismo.getReferenciaLocalizacion())
                .estatus(sismo.getEstatus())
                .placaId(sismo.getPlacaId())
                .build();
    }

}


