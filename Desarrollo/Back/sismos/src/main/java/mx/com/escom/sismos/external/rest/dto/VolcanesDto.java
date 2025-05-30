package mx.com.escom.sismos.external.rest.dto;

import lombok.*;
import mx.com.escom.sismos.core.entity.Volcan;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class VolcanesDto {
    private Integer id;
    private String descripcion;
    private BigDecimal latitud;
    private BigDecimal longitud;

    public static VolcanesDto fromEntity (Volcan entity){
        return VolcanesDto.builder()
                .id(entity.getId())
                .descripcion(entity.getNombre())
                .latitud(entity.getLatitud())
                .longitud(entity.getLongitud())
                .build();
    }
}
