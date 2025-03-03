package mx.com.tarea3.Tarea.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class Rol {
    private Integer id;
    private String nombre;
}
