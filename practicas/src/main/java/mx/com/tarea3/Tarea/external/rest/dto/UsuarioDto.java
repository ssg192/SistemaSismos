package mx.com.tarea3.Tarea.external.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mx.com.tarea3.Tarea.core.entity.Usuario;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    @JsonProperty
    private String nombre;
    @JsonProperty
    private String email;
    @JsonProperty
    private String password;

    public static Usuario fromEntity(Usuario usuario) {
        return Usuario.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .password(usuario.getPassword())
                .roles(usuario.getRoles())
                .build();
    }
    public Usuario toEntity() {
        return Usuario.builder()
                .nombre(this.nombre)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
