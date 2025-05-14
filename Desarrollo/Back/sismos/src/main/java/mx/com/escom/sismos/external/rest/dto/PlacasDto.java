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
public class PlacasDto {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String nombre;
    @JsonProperty
    private String descripcion;
    @JsonProperty
    private String ubicacion;

    public static PlacasDto fromEntity(Placas placas) {
        return PlacasDto.builder()
                .id(placas.getId())
                .nombre(placas.getNombre())
                .descripcion(placas.getDescripcion())
                .ubicacion(placas.getUbicacion())
                .build();
    }
}
