package mx.com.escom.sismos.external.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.arc.All;
import lombok.*;
import mx.com.escom.sismos.core.entity.Sismo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SismoWithVolcanAndPlacaDto {
    @JsonProperty
    private String nombrePlaca;
    @JsonProperty
    private String geomPlaca;
    @JsonProperty
    private LocalDate fecha;
    @JsonProperty
    private BigDecimal magnitud;
    @JsonProperty
    private BigDecimal latitud;
    @JsonProperty
    private BigDecimal longitud;
    @JsonProperty
    private String referenciaLocalizacion;
    @JsonProperty
    private String estatus;
    @JsonProperty
    private List<String> nombreVolcanes;
    @JsonProperty
    private List<BigDecimal> latitudVolcanes;
    @JsonProperty
    private List<BigDecimal> longitudVolcanes;

    public static SismoWithVolcanAndPlacaDto fromEntity(Sismo sismo) {
        return SismoWithVolcanAndPlacaDto.builder()
                .nombrePlaca(sismo.getPlacaNombre())
                .geomPlaca(sismo.getGeomPlaca())
                .fecha(sismo.getFecha())
                .magnitud(sismo.getMagnitud())
                .latitud(sismo.getLatitud())
                .longitud(sismo.getLongitud())
                .referenciaLocalizacion(sismo.getReferenciaLocalizacion())
                .estatus(sismo.getEstatus())
                .nombreVolcanes(sismo.getNombreVolcanes())
                .latitudVolcanes(sismo.getLatitudVolcanes())
                .longitudVolcanes(sismo.getLongitudVolcanes())
                .build();
    }
}
