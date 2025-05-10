package mx.com.escom.sismos.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Placas {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
}
