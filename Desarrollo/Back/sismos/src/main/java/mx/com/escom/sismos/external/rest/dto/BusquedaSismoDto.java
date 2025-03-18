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
    private LocalDate fecha;
    @JsonProperty
    private LocalTime hora;
    @JsonProperty
    private BigDecimal magnitud;
    @JsonProperty
    private String referenciaLocalizacion;


    public static BusquedaSismoDto fromEntity(Sismo sismo) {
        return BusquedaSismoDto.builder()
                .fecha(sismo.getFecha())
                .hora(sismo.getHora())
                .magnitud(sismo.getMagnitud())
                .referenciaLocalizacion(sismo.getReferenciaLocalizacion())
                .build();
    }

    public Sismo toEntity() {
        return Sismo.builder()
                .fecha(fecha)
                .hora(hora)
                .magnitud(magnitud)
                .referenciaLocalizacion(referenciaLocalizacion)
                .build();
    }


}
