package mx.com.tarea3.Tarea.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class Usuario {
   private Integer id;
   private String nombre;
   private String email;
   private String password;
   private List<Rol> roles;
}
