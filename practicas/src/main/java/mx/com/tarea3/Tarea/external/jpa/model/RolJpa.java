package mx.com.tarea3.Tarea.external.jpa.model;

import lombok.*;
import mx.com.tarea3.Tarea.core.entity.Rol;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RolJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    public static RolJpa fromEntity(Rol rol) {
        return RolJpa.builder()
                .id(rol.getId())
                .nombre(rol.getNombre())
                .build();
    }

    public Rol toEntity() {
        return Rol.builder()
                .id(this.id)
                .nombre(this.nombre)
                .build();
    }

}