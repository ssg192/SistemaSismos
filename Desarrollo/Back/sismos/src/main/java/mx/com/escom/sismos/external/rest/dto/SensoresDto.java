package mx.com.escom.sismos.external.rest.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mx.com.escom.sismos.core.entity.Sensores;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensoresDto {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String codigo;
    @JsonProperty
    private String nombre;
    @JsonProperty
    private String estado;
    @JsonProperty
    private BigDecimal latitud;
    @JsonProperty
    private BigDecimal longitud;
    @JsonProperty
    private String red;

    public static SensoresDto fromEntity(Sensores sensores) {
        return SensoresDto.builder()
                .id(sensores.getId())
                .codigo(sensores.getCodigo())
                .nombre(sensores.getNombre())
                .estado(sensores.getEstado())
                .latitud(sensores.getLatitud())
                .longitud(sensores.getLongitud())
                .red(sensores.getRed())
                .build();
    }
}
