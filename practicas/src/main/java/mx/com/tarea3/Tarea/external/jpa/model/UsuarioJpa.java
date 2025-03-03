package mx.com.tarea3.Tarea.external.jpa.model;

import lombok.*;
import mx.com.tarea3.Tarea.core.entity.Rol;
import mx.com.tarea3.Tarea.core.entity.Usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class UsuarioJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer Id;
    @Column(name= "nombre")
    private String nombre;
    @Column(name= "email")
    private String email;
    @Column(name= "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id")
    )
    private List<RolJpa> roles;

    public static UsuarioJpa fromEntity(Usuario usuario) {
        return UsuarioJpa.builder()
                .Id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getRoles().stream().map(RolJpa::fromEntity).collect(Collectors.toList()))
                .build();
    }


    public Usuario toEntity(){
        return Usuario.builder()
                .id(Id)
                .nombre(this.nombre)
                .email(this.email)
                .password(this.password)
                .roles(this.roles.stream().map(RolJpa::toEntity).toList())
                .build();
    }

}
