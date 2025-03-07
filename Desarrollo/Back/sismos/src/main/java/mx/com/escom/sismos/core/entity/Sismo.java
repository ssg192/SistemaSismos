package mx.com.escom.sismos.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sismo {
    private Integer id;
    private LocalDate fecha;
    private LocalTime hora;
    private BigDecimal magnitud;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private BigDecimal profundidad;
    private String referenciaLocalizacion;
    private String estatus;
    private String identificador;
    private Integer placaId;
}

