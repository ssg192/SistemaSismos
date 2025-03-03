package mx.com.tarea3.Tarea.external.rest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mx.com.tarea3.Tarea.core.entity.Usuario;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InicioSesionDto {

    @JsonProperty
    private String email;
    @JsonProperty
    private String password;


    public static InicioSesionDto fromEntity(Usuario usuario) {
        return InicioSesionDto.builder()
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .build();
    }
    public Usuario toEntity() {
        return Usuario.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}
