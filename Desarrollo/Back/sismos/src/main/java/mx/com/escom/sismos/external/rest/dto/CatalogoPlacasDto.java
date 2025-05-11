package mx.com.escom.sismos.external.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.escom.sismos.core.entity.Placas;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoPlacasDto {

    @JsonProperty
    private Integer id;
    @JsonProperty
    private String nombre;
    @JsonProperty
    private String descripcion;

    public static CatalogoPlacasDto fromEntity(Placas placas) {
        return CatalogoPlacasDto.builder()
                .id(placas.getId())
                .nombre(placas.getNombre())
                .descripcion(placas.getDescripcion())
                .build();
    }
}
