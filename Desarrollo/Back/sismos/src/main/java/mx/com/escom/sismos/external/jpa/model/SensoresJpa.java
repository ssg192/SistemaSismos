package mx.com.escom.sismos.external.jpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import mx.com.escom.sismos.core.entity.Sensores;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sensores")
public class SensoresJpa {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private String estado;
    @Column(name = "latitud")
    private BigDecimal latitud;
    @Column(name = "longitud")
    private BigDecimal longitud;
    @Column(name = "red")
    private String red;

    public static SensoresJpa fromEntity(Sensores sensores) {
        return SensoresJpa.builder()
                .id(sensores.getId())
                .codigo(sensores.getCodigo())
                .nombre(sensores.getNombre())
                .estado(sensores.getEstado())
                .latitud(sensores.getLatitud())
                .longitud(sensores.getLongitud())
                .red(sensores.getRed())
                .build();
    }
    
    
    public Sensores toEntity(){
        return Sensores.builder()
                .id(this.id)
                .codigo(this.codigo)
                .nombre(this.nombre)
                .estado(this.estado)
                .latitud(this.latitud)
                .longitud(this.longitud)
                .red(this.red)
                .build();
    }
}