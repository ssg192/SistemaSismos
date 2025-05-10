package mx.com.escom.sismos.external.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.escom.sismos.core.entity.Placas;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "placas")
public class PlacasJpa {
    @Id
    @Column(name = "placa_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "geom")
    private String ubicacion;

    public static PlacasJpa fromEntity(Placas placas) {
        return PlacasJpa.builder()
                .id(placas.getId())
                .nombre(placas.getNombre())
                .descripcion(placas.getDescripcion())
                .ubicacion(placas.getUbicacion())
                .build();
    }

    public Placas toEntity() {
        return Placas.builder()
                .id(this.id)
                .nombre(this.nombre)
                .descripcion(this.descripcion)
                .ubicacion(this.ubicacion)
                .build();
    }
}
