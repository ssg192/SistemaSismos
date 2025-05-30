package mx.com.escom.sismos.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Volcan {
    private Integer id;
    private String nombre;
    private BigDecimal latitud;
    private BigDecimal longitud;
}
