package mx.com.escom.sismos.external.jpa.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.escom.sismos.core.entity.Sismo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registros_sismos")
public class SismoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "hora")
    private LocalTime hora;
    @Column(name = "magnitud")
    private BigDecimal magnitud;
    @Column(name = "latitud")
    private BigDecimal latitud;
    @Column(name = "longitud")
    private BigDecimal longitud;
    @Column(name = "profundidad")
    private BigDecimal profundidad;
    @Column(name = "referencia_localizacion")
    private String referenciaLocalizacion;
    @Column(name = "estatus")
    private String estatus;
    @Column(name = "identificador")
    private String identificador;
    @Column(name = "placa_id")
    private Integer placaId;

    public static SismoJpa fromEntity(SismoJpa sismo) {
        return SismoJpa.builder()
                .id(sismo.getId())
                .fecha(sismo.getFecha())
                .hora(sismo.getHora())
                .magnitud(sismo.getMagnitud())
                .latitud(sismo.getLatitud())
                .longitud(sismo.getLongitud())
                .profundidad(sismo.getProfundidad())
                .referenciaLocalizacion(sismo.getReferenciaLocalizacion())
                .estatus(sismo.getEstatus())
                .identificador(sismo.getIdentificador())
                .placaId(sismo.getPlacaId())
                .build();
    }

    public Sismo toEntity(){
        return Sismo.builder()
                .id(id)
                .fecha(fecha)
                .hora(hora)
                .magnitud(magnitud)
                .latitud(latitud)
                .longitud(longitud)
                .profundidad(profundidad)
                .referenciaLocalizacion(referenciaLocalizacion)
                .estatus(estatus)
                .identificador(identificador)
                .placaId(placaId)
                .build();
    }
}
