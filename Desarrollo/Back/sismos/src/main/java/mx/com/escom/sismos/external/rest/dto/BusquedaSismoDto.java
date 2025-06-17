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
public class BusquedaSismoDto {

    @JsonProperty
    private Integer id;
    @JsonProperty
    private LocalTime hora;
    @JsonProperty
    private BigDecimal magnitud;
    @JsonProperty
    private String referenciaLocalizacion;
    @JsonProperty
    private BigDecimal latitud;
    @JsonProperty
    private BigDecimal longitud;
    @JsonProperty
    private String nombrePlaca;
    @JsonProperty
    private LocalDate fecha;

    public static BusquedaSismoDto fromEntity(Sismo sismo) {
        return BusquedaSismoDto.builder()
                .id(sismo.getId())
                .hora(sismo.getHora())
                .magnitud(sismo.getMagnitud())
                .referenciaLocalizacion(sismo.getReferenciaLocalizacion())
                .latitud(sismo.getLatitud())
                .longitud(sismo.getLongitud())
                .nombrePlaca(sismo.getPlacas().getNombre())
                .fecha(sismo.getFecha())
                .build();
    }




}
