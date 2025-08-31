package mx.com.escom.sismos.core.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sensores {
    private Integer id;
    private String codigo;
    private String nombre;
    private String estado;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String red;
    private String nombrePruebas55678;
}
